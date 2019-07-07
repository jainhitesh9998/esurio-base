import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { IEsuriits } from 'app/shared/model/esuriits.model';
import { EsuriitsService } from 'app/entities/esuriits';
import { IOutlets } from 'app/shared/model/outlets.model';
import { OutletsService } from 'app/entities/outlets';
import { IAttendants } from 'app/shared/model/attendants.model';
import { AttendantsService } from 'app/entities/attendants';

@Component({
  selector: 'jhi-orders-update',
  templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  esuriits: IEsuriits[];

  outlets: IOutlets[];

  attendants: IAttendants[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    time: [],
    takeaway: [],
    scheduled: [],
    confirmed: [],
    delivered: [],
    esuriitId: [],
    outletId: [],
    attendantId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ordersService: OrdersService,
    protected esuriitsService: EsuriitsService,
    protected outletsService: OutletsService,
    protected attendantsService: AttendantsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orders }) => {
      this.updateForm(orders);
    });
    this.esuriitsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsuriits[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsuriits[]>) => response.body)
      )
      .subscribe((res: IEsuriits[]) => (this.esuriits = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.outletsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOutlets[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOutlets[]>) => response.body)
      )
      .subscribe((res: IOutlets[]) => (this.outlets = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.attendantsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAttendants[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAttendants[]>) => response.body)
      )
      .subscribe((res: IAttendants[]) => (this.attendants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orders: IOrders) {
    this.editForm.patchValue({
      id: orders.id,
      identifier: orders.identifier,
      time: orders.time != null ? orders.time.format(DATE_TIME_FORMAT) : null,
      takeaway: orders.takeaway,
      scheduled: orders.scheduled != null ? orders.scheduled.format(DATE_TIME_FORMAT) : null,
      confirmed: orders.confirmed,
      delivered: orders.delivered,
      esuriitId: orders.esuriitId,
      outletId: orders.outletId,
      attendantId: orders.attendantId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orders = this.createFromForm();
    if (orders.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersService.update(orders));
    } else {
      this.subscribeToSaveResponse(this.ordersService.create(orders));
    }
  }

  private createFromForm(): IOrders {
    return {
      ...new Orders(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      time: this.editForm.get(['time']).value != null ? moment(this.editForm.get(['time']).value, DATE_TIME_FORMAT) : undefined,
      takeaway: this.editForm.get(['takeaway']).value,
      scheduled:
        this.editForm.get(['scheduled']).value != null ? moment(this.editForm.get(['scheduled']).value, DATE_TIME_FORMAT) : undefined,
      confirmed: this.editForm.get(['confirmed']).value,
      delivered: this.editForm.get(['delivered']).value,
      esuriitId: this.editForm.get(['esuriitId']).value,
      outletId: this.editForm.get(['outletId']).value,
      attendantId: this.editForm.get(['attendantId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEsuriitsById(index: number, item: IEsuriits) {
    return item.id;
  }

  trackOutletsById(index: number, item: IOutlets) {
    return item.id;
  }

  trackAttendantsById(index: number, item: IAttendants) {
    return item.id;
  }
}
