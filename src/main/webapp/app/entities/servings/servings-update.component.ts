import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServings, Servings } from 'app/shared/model/servings.model';
import { ServingsService } from './servings.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders';
import { IDishes } from 'app/shared/model/dishes.model';
import { DishesService } from 'app/entities/dishes';
import { IAttendants } from 'app/shared/model/attendants.model';
import { AttendantsService } from 'app/entities/attendants';

@Component({
  selector: 'jhi-servings-update',
  templateUrl: './servings-update.component.html'
})
export class ServingsUpdateComponent implements OnInit {
  isSaving: boolean;

  orders: IOrders[];

  dishes: IDishes[];

  attendants: IAttendants[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    prepared: [],
    served: [],
    quantity: [],
    orderId: [],
    dishId: [],
    attendantId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected servingsService: ServingsService,
    protected ordersService: OrdersService,
    protected dishesService: DishesService,
    protected attendantsService: AttendantsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ servings }) => {
      this.updateForm(servings);
    });
    this.ordersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrders[]>) => response.body)
      )
      .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.dishesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDishes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDishes[]>) => response.body)
      )
      .subscribe((res: IDishes[]) => (this.dishes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.attendantsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAttendants[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAttendants[]>) => response.body)
      )
      .subscribe((res: IAttendants[]) => (this.attendants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(servings: IServings) {
    this.editForm.patchValue({
      id: servings.id,
      identifier: servings.identifier,
      prepared: servings.prepared,
      served: servings.served,
      quantity: servings.quantity,
      orderId: servings.orderId,
      dishId: servings.dishId,
      attendantId: servings.attendantId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const servings = this.createFromForm();
    if (servings.id !== undefined) {
      this.subscribeToSaveResponse(this.servingsService.update(servings));
    } else {
      this.subscribeToSaveResponse(this.servingsService.create(servings));
    }
  }

  private createFromForm(): IServings {
    return {
      ...new Servings(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      prepared: this.editForm.get(['prepared']).value,
      served: this.editForm.get(['served']).value,
      quantity: this.editForm.get(['quantity']).value,
      orderId: this.editForm.get(['orderId']).value,
      dishId: this.editForm.get(['dishId']).value,
      attendantId: this.editForm.get(['attendantId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServings>>) {
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

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }

  trackDishesById(index: number, item: IDishes) {
    return item.id;
  }

  trackAttendantsById(index: number, item: IAttendants) {
    return item.id;
  }
}
