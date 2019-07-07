import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMenus, Menus } from 'app/shared/model/menus.model';
import { MenusService } from './menus.service';
import { IOutlets } from 'app/shared/model/outlets.model';
import { OutletsService } from 'app/entities/outlets';

@Component({
  selector: 'jhi-menus-update',
  templateUrl: './menus-update.component.html'
})
export class MenusUpdateComponent implements OnInit {
  isSaving: boolean;

  outlets: IOutlets[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    active: [],
    outletId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected menusService: MenusService,
    protected outletsService: OutletsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ menus }) => {
      this.updateForm(menus);
    });
    this.outletsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOutlets[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOutlets[]>) => response.body)
      )
      .subscribe((res: IOutlets[]) => (this.outlets = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(menus: IMenus) {
    this.editForm.patchValue({
      id: menus.id,
      identifier: menus.identifier,
      active: menus.active,
      outletId: menus.outletId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const menus = this.createFromForm();
    if (menus.id !== undefined) {
      this.subscribeToSaveResponse(this.menusService.update(menus));
    } else {
      this.subscribeToSaveResponse(this.menusService.create(menus));
    }
  }

  private createFromForm(): IMenus {
    return {
      ...new Menus(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      active: this.editForm.get(['active']).value,
      outletId: this.editForm.get(['outletId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenus>>) {
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
