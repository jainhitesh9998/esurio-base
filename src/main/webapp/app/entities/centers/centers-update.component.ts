import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICenters, Centers } from 'app/shared/model/centers.model';
import { CentersService } from './centers.service';

@Component({
  selector: 'jhi-centers-update',
  templateUrl: './centers-update.component.html'
})
export class CentersUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]]
  });

  constructor(protected centersService: CentersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ centers }) => {
      this.updateForm(centers);
    });
  }

  updateForm(centers: ICenters) {
    this.editForm.patchValue({
      id: centers.id,
      identifier: centers.identifier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const centers = this.createFromForm();
    if (centers.id !== undefined) {
      this.subscribeToSaveResponse(this.centersService.update(centers));
    } else {
      this.subscribeToSaveResponse(this.centersService.create(centers));
    }
  }

  private createFromForm(): ICenters {
    return {
      ...new Centers(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICenters>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
