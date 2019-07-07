import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICenters } from 'app/shared/model/centers.model';
import { AccountService } from 'app/core';
import { CentersService } from './centers.service';

@Component({
  selector: 'jhi-centers',
  templateUrl: './centers.component.html'
})
export class CentersComponent implements OnInit, OnDestroy {
  centers: ICenters[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected centersService: CentersService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.centersService
      .query()
      .pipe(
        filter((res: HttpResponse<ICenters[]>) => res.ok),
        map((res: HttpResponse<ICenters[]>) => res.body)
      )
      .subscribe(
        (res: ICenters[]) => {
          this.centers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCenters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICenters) {
    return item.id;
  }

  registerChangeInCenters() {
    this.eventSubscriber = this.eventManager.subscribe('centersListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
