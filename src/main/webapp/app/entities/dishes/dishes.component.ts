import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDishes } from 'app/shared/model/dishes.model';
import { AccountService } from 'app/core';
import { DishesService } from './dishes.service';

@Component({
  selector: 'jhi-dishes',
  templateUrl: './dishes.component.html'
})
export class DishesComponent implements OnInit, OnDestroy {
  dishes: IDishes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dishesService: DishesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dishesService
      .query()
      .pipe(
        filter((res: HttpResponse<IDishes[]>) => res.ok),
        map((res: HttpResponse<IDishes[]>) => res.body)
      )
      .subscribe(
        (res: IDishes[]) => {
          this.dishes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDishes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDishes) {
    return item.id;
  }

  registerChangeInDishes() {
    this.eventSubscriber = this.eventManager.subscribe('dishesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
