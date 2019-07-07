import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAttendants } from 'app/shared/model/attendants.model';
import { AccountService } from 'app/core';
import { AttendantsService } from './attendants.service';

@Component({
  selector: 'jhi-attendants',
  templateUrl: './attendants.component.html'
})
export class AttendantsComponent implements OnInit, OnDestroy {
  attendants: IAttendants[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected attendantsService: AttendantsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.attendantsService
      .query()
      .pipe(
        filter((res: HttpResponse<IAttendants[]>) => res.ok),
        map((res: HttpResponse<IAttendants[]>) => res.body)
      )
      .subscribe(
        (res: IAttendants[]) => {
          this.attendants = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAttendants();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAttendants) {
    return item.id;
  }

  registerChangeInAttendants() {
    this.eventSubscriber = this.eventManager.subscribe('attendantsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
