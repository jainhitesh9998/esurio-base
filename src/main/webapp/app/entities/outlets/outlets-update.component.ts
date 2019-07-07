import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOutlets, Outlets } from 'app/shared/model/outlets.model';
import { OutletsService } from './outlets.service';
import { IFoodcourts } from 'app/shared/model/foodcourts.model';
import { FoodcourtsService } from 'app/entities/foodcourts';
import { IVendors } from 'app/shared/model/vendors.model';
import { VendorsService } from 'app/entities/vendors';

@Component({
  selector: 'jhi-outlets-update',
  templateUrl: './outlets-update.component.html'
})
export class OutletsUpdateComponent implements OnInit {
  isSaving: boolean;

  foodcourts: IFoodcourts[];

  vendors: IVendors[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    foodcourtId: [],
    vendorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected outletsService: OutletsService,
    protected foodcourtsService: FoodcourtsService,
    protected vendorsService: VendorsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ outlets }) => {
      this.updateForm(outlets);
    });
    this.foodcourtsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFoodcourts[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFoodcourts[]>) => response.body)
      )
      .subscribe((res: IFoodcourts[]) => (this.foodcourts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.vendorsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVendors[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVendors[]>) => response.body)
      )
      .subscribe((res: IVendors[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(outlets: IOutlets) {
    this.editForm.patchValue({
      id: outlets.id,
      identifier: outlets.identifier,
      foodcourtId: outlets.foodcourtId,
      vendorId: outlets.vendorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const outlets = this.createFromForm();
    if (outlets.id !== undefined) {
      this.subscribeToSaveResponse(this.outletsService.update(outlets));
    } else {
      this.subscribeToSaveResponse(this.outletsService.create(outlets));
    }
  }

  private createFromForm(): IOutlets {
    return {
      ...new Outlets(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      foodcourtId: this.editForm.get(['foodcourtId']).value,
      vendorId: this.editForm.get(['vendorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutlets>>) {
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

  trackFoodcourtsById(index: number, item: IFoodcourts) {
    return item.id;
  }

  trackVendorsById(index: number, item: IVendors) {
    return item.id;
  }
}
