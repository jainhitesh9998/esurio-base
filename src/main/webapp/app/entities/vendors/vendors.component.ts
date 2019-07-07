import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVendors } from 'app/shared/model/vendors.model';
import { AccountService } from 'app/core';
import { VendorsService } from './vendors.service';

@Component({
  selector: 'jhi-vendors',
  templateUrl: './vendors.component.html'
})
export class VendorsComponent implements OnInit, OnDestroy {
  vendors: IVendors[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected vendorsService: VendorsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.vendorsService
      .query()
      .pipe(
        filter((res: HttpResponse<IVendors[]>) => res.ok),
        map((res: HttpResponse<IVendors[]>) => res.body)
      )
      .subscribe(
        (res: IVendors[]) => {
          this.vendors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVendors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVendors) {
    return item.id;
  }

  registerChangeInVendors() {
    this.eventSubscriber = this.eventManager.subscribe('vendorsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
