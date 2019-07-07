import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IFoodcourts } from 'app/shared/model/foodcourts.model';
import { AccountService } from 'app/core';
import { FoodcourtsService } from './foodcourts.service';

@Component({
  selector: 'jhi-foodcourts',
  templateUrl: './foodcourts.component.html'
})
export class FoodcourtsComponent implements OnInit, OnDestroy {
  foodcourts: IFoodcourts[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected foodcourtsService: FoodcourtsService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.foodcourtsService
      .query()
      .pipe(
        filter((res: HttpResponse<IFoodcourts[]>) => res.ok),
        map((res: HttpResponse<IFoodcourts[]>) => res.body)
      )
      .subscribe(
        (res: IFoodcourts[]) => {
          this.foodcourts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFoodcourts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFoodcourts) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInFoodcourts() {
    this.eventSubscriber = this.eventManager.subscribe('foodcourtsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
