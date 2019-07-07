import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IItems, Items } from 'app/shared/model/items.model';
import { ItemsService } from './items.service';
import { IVendors } from 'app/shared/model/vendors.model';
import { VendorsService } from 'app/entities/vendors';

@Component({
  selector: 'jhi-items-update',
  templateUrl: './items-update.component.html'
})
export class ItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  vendors: IVendors[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    vendorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected itemsService: ItemsService,
    protected vendorsService: VendorsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ items }) => {
      this.updateForm(items);
    });
    this.vendorsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVendors[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVendors[]>) => response.body)
      )
      .subscribe((res: IVendors[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(items: IItems) {
    this.editForm.patchValue({
      id: items.id,
      identifier: items.identifier,
      image: items.image,
      imageContentType: items.imageContentType,
      vendorId: items.vendorId
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
    const items = this.createFromForm();
    if (items.id !== undefined) {
      this.subscribeToSaveResponse(this.itemsService.update(items));
    } else {
      this.subscribeToSaveResponse(this.itemsService.create(items));
    }
  }

  private createFromForm(): IItems {
    return {
      ...new Items(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      vendorId: this.editForm.get(['vendorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItems>>) {
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

  trackVendorsById(index: number, item: IVendors) {
    return item.id;
  }
}
