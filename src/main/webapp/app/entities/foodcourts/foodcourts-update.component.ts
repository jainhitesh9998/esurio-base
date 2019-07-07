import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFoodcourts, Foodcourts } from 'app/shared/model/foodcourts.model';
import { FoodcourtsService } from './foodcourts.service';
import { ICenters } from 'app/shared/model/centers.model';
import { CentersService } from 'app/entities/centers';

@Component({
  selector: 'jhi-foodcourts-update',
  templateUrl: './foodcourts-update.component.html'
})
export class FoodcourtsUpdateComponent implements OnInit {
  isSaving: boolean;

  centers: ICenters[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    centerId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected foodcourtsService: FoodcourtsService,
    protected centersService: CentersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ foodcourts }) => {
      this.updateForm(foodcourts);
    });
    this.centersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICenters[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICenters[]>) => response.body)
      )
      .subscribe((res: ICenters[]) => (this.centers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(foodcourts: IFoodcourts) {
    this.editForm.patchValue({
      id: foodcourts.id,
      identifier: foodcourts.identifier,
      image: foodcourts.image,
      imageContentType: foodcourts.imageContentType,
      centerId: foodcourts.centerId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const foodcourts = this.createFromForm();
    if (foodcourts.id !== undefined) {
      this.subscribeToSaveResponse(this.foodcourtsService.update(foodcourts));
    } else {
      this.subscribeToSaveResponse(this.foodcourtsService.create(foodcourts));
    }
  }

  private createFromForm(): IFoodcourts {
    return {
      ...new Foodcourts(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      centerId: this.editForm.get(['centerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodcourts>>) {
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
