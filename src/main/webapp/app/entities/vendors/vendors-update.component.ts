import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVendors, Vendors } from 'app/shared/model/vendors.model';
import { VendorsService } from './vendors.service';

@Component({
  selector: 'jhi-vendors-update',
  templateUrl: './vendors-update.component.html'
})
export class VendorsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]]
  });

  constructor(protected vendorsService: VendorsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vendors }) => {
      this.updateForm(vendors);
    });
  }

  updateForm(vendors: IVendors) {
    this.editForm.patchValue({
      id: vendors.id,
      identifier: vendors.identifier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vendors = this.createFromForm();
    if (vendors.id !== undefined) {
      this.subscribeToSaveResponse(this.vendorsService.update(vendors));
    } else {
      this.subscribeToSaveResponse(this.vendorsService.create(vendors));
    }
  }

  private createFromForm(): IVendors {
    return {
      ...new Vendors(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVendors>>) {
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
