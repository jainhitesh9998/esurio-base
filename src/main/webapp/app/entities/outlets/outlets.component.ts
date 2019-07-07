import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOutlets } from 'app/shared/model/outlets.model';
import { AccountService } from 'app/core';
import { OutletsService } from './outlets.service';

@Component({
  selector: 'jhi-outlets',
  templateUrl: './outlets.component.html'
})
export class OutletsComponent implements OnInit, OnDestroy {
  outlets: IOutlets[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected outletsService: OutletsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.outletsService
      .query()
      .pipe(
        filter((res: HttpResponse<IOutlets[]>) => res.ok),
        map((res: HttpResponse<IOutlets[]>) => res.body)
      )
      .subscribe(
        (res: IOutlets[]) => {
          this.outlets = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOutlets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOutlets) {
    return item.id;
  }

  registerChangeInOutlets() {
    this.eventSubscriber = this.eventManager.subscribe('outletsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
