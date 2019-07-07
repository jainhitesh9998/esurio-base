import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAttendants, Attendants } from 'app/shared/model/attendants.model';
import { AttendantsService } from './attendants.service';
import { IOutlets } from 'app/shared/model/outlets.model';
import { OutletsService } from 'app/entities/outlets';

@Component({
  selector: 'jhi-attendants-update',
  templateUrl: './attendants-update.component.html'
})
export class AttendantsUpdateComponent implements OnInit {
  isSaving: boolean;

  outlets: IOutlets[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    outletId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected attendantsService: AttendantsService,
    protected outletsService: OutletsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ attendants }) => {
      this.updateForm(attendants);
    });
    this.outletsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOutlets[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOutlets[]>) => response.body)
      )
      .subscribe((res: IOutlets[]) => (this.outlets = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(attendants: IAttendants) {
    this.editForm.patchValue({
      id: attendants.id,
      identifier: attendants.identifier,
      outletId: attendants.outletId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const attendants = this.createFromForm();
    if (attendants.id !== undefined) {
      this.subscribeToSaveResponse(this.attendantsService.update(attendants));
    } else {
      this.subscribeToSaveResponse(this.attendantsService.create(attendants));
    }
  }

  private createFromForm(): IAttendants {
    return {
      ...new Attendants(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      outletId: this.editForm.get(['outletId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendants>>) {
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

  trackOutletsById(index: number, item: IOutlets) {
    return item.id;
  }
}
