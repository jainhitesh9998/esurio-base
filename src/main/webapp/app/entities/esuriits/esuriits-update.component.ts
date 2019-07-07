import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEsuriits, Esuriits } from 'app/shared/model/esuriits.model';
import { EsuriitsService } from './esuriits.service';
import { ICenters } from 'app/shared/model/centers.model';
import { CentersService } from 'app/entities/centers';

@Component({
  selector: 'jhi-esuriits-update',
  templateUrl: './esuriits-update.component.html'
})
export class EsuriitsUpdateComponent implements OnInit {
  isSaving: boolean;

  centers: ICenters[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    centerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected esuriitsService: EsuriitsService,
    protected centersService: CentersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ esuriits }) => {
      this.updateForm(esuriits);
    });
    this.centersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICenters[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICenters[]>) => response.body)
      )
      .subscribe((res: ICenters[]) => (this.centers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(esuriits: IEsuriits) {
    this.editForm.patchValue({
      id: esuriits.id,
      identifier: esuriits.identifier,
      centerId: esuriits.centerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const esuriits = this.createFromForm();
    if (esuriits.id !== undefined) {
      this.subscribeToSaveResponse(this.esuriitsService.update(esuriits));
    } else {
      this.subscribeToSaveResponse(this.esuriitsService.create(esuriits));
    }
  }

  private createFromForm(): IEsuriits {
    return {
      ...new Esuriits(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      centerId: this.editForm.get(['centerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEsuriits>>) {
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

  trackCentersById(index: number, item: ICenters) {
    return item.id;
  }
}
