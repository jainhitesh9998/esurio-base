import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMenus } from 'app/shared/model/menus.model';
import { AccountService } from 'app/core';
import { MenusService } from './menus.service';

@Component({
  selector: 'jhi-menus',
  templateUrl: './menus.component.html'
})
export class MenusComponent implements OnInit, OnDestroy {
  menus: IMenus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected menusService: MenusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.menusService
      .query()
      .pipe(
        filter((res: HttpResponse<IMenus[]>) => res.ok),
        map((res: HttpResponse<IMenus[]>) => res.body)
      )
      .subscribe(
        (res: IMenus[]) => {
          this.menus = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMenus();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMenus) {
    return item.id;
  }

  registerChangeInMenus() {
    this.eventSubscriber = this.eventManager.subscribe('menusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
