import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDishes, Dishes } from 'app/shared/model/dishes.model';
import { DishesService } from './dishes.service';
import { IMenus } from 'app/shared/model/menus.model';
import { MenusService } from 'app/entities/menus';
import { IItems } from 'app/shared/model/items.model';
import { ItemsService } from 'app/entities/items';

@Component({
  selector: 'jhi-dishes-update',
  templateUrl: './dishes-update.component.html'
})
export class DishesUpdateComponent implements OnInit {
  isSaving: boolean;

  menus: IMenus[];

  items: IItems[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    takeaway: [],
    servings: [],
    menuId: [],
    itemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dishesService: DishesService,
    protected menusService: MenusService,
    protected itemsService: ItemsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dishes }) => {
      this.updateForm(dishes);
    });
    this.menusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMenus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMenus[]>) => response.body)
      )
      .subscribe((res: IMenus[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.itemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IItems[]>) => response.body)
      )
      .subscribe((res: IItems[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dishes: IDishes) {
    this.editForm.patchValue({
      id: dishes.id,
      identifier: dishes.identifier,
      takeaway: dishes.takeaway,
      servings: dishes.servings,
      menuId: dishes.menuId,
      itemId: dishes.itemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dishes = this.createFromForm();
    if (dishes.id !== undefined) {
      this.subscribeToSaveResponse(this.dishesService.update(dishes));
    } else {
      this.subscribeToSaveResponse(this.dishesService.create(dishes));
    }
  }

  private createFromForm(): IDishes {
    return {
      ...new Dishes(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      takeaway: this.editForm.get(['takeaway']).value,
      servings: this.editForm.get(['servings']).value,
      menuId: this.editForm.get(['menuId']).value,
      itemId: this.editForm.get(['itemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDishes>>) {
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

  trackMenusById(index: number, item: IMenus) {
    return item.id;
  }

  trackItemsById(index: number, item: IItems) {
    return item.id;
  }
}
