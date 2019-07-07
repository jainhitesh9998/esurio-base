import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEsuriits } from 'app/shared/model/esuriits.model';
import { AccountService } from 'app/core';
import { EsuriitsService } from './esuriits.service';

@Component({
  selector: 'jhi-esuriits',
  templateUrl: './esuriits.component.html'
})
export class EsuriitsComponent implements OnInit, OnDestroy {
  esuriits: IEsuriits[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected esuriitsService: EsuriitsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.esuriitsService
      .query()
      .pipe(
        filter((res: HttpResponse<IEsuriits[]>) => res.ok),
        map((res: HttpResponse<IEsuriits[]>) => res.body)
      )
      .subscribe(
        (res: IEsuriits[]) => {
          this.esuriits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEsuriits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEsuriits) {
    return item.id;
  }

  registerChangeInEsuriits() {
    this.eventSubscriber = this.eventManager.subscribe('esuriitsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
