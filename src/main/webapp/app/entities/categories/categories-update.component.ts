import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICategories, Categories } from 'app/shared/model/categories.model';
import { CategoriesService } from './categories.service';
import { IItems } from 'app/shared/model/items.model';
import { ItemsService } from 'app/entities/items';
import { ITags } from 'app/shared/model/tags.model';
import { TagsService } from 'app/entities/tags';

@Component({
  selector: 'jhi-categories-update',
  templateUrl: './categories-update.component.html'
})
export class CategoriesUpdateComponent implements OnInit {
  isSaving: boolean;

  items: IItems[];

  tags: ITags[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    itemId: [],
    tagId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected categoriesService: CategoriesService,
    protected itemsService: ItemsService,
    protected tagsService: TagsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categories }) => {
      this.updateForm(categories);
    });
    this.itemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IItems[]>) => response.body)
      )
      .subscribe((res: IItems[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tagsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITags[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITags[]>) => response.body)
      )
      .subscribe((res: ITags[]) => (this.tags = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(categories: ICategories) {
    this.editForm.patchValue({
      id: categories.id,
      identifier: categories.identifier,
      itemId: categories.itemId,
      tagId: categories.tagId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categories = this.createFromForm();
    if (categories.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriesService.update(categories));
    } else {
      this.subscribeToSaveResponse(this.categoriesService.create(categories));
    }
  }

  private createFromForm(): ICategories {
    return {
      ...new Categories(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      itemId: this.editForm.get(['itemId']).value,
      tagId: this.editForm.get(['tagId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategories>>) {
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

  trackItemsById(index: number, item: IItems) {
    return item.id;
  }

  trackTagsById(index: number, item: ITags) {
    return item.id;
  }
}
