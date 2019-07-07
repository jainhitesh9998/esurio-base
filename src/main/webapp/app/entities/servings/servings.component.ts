import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServings } from 'app/shared/model/servings.model';
import { AccountService } from 'app/core';
import { ServingsService } from './servings.service';

@Component({
  selector: 'jhi-servings',
  templateUrl: './servings.component.html'
})
export class ServingsComponent implements OnInit, OnDestroy {
  servings: IServings[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected servingsService: ServingsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.servingsService
      .query()
      .pipe(
        filter((res: HttpResponse<IServings[]>) => res.ok),
        map((res: HttpResponse<IServings[]>) => res.body)
      )
      .subscribe(
        (res: IServings[]) => {
          this.servings = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInServings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServings) {
    return item.id;
  }

  registerChangeInServings() {
    this.eventSubscriber = this.eventManager.subscribe('servingsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
