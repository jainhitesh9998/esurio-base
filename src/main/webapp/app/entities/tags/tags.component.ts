import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITags } from 'app/shared/model/tags.model';
import { AccountService } from 'app/core';
import { TagsService } from './tags.service';

@Component({
  selector: 'jhi-tags',
  templateUrl: './tags.component.html'
})
export class TagsComponent implements OnInit, OnDestroy {
  tags: ITags[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tagsService: TagsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tagsService
      .query()
      .pipe(
        filter((res: HttpResponse<ITags[]>) => res.ok),
        map((res: HttpResponse<ITags[]>) => res.body)
      )
      .subscribe(
        (res: ITags[]) => {
          this.tags = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTags();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITags) {
    return item.id;
  }

  registerChangeInTags() {
    this.eventSubscriber = this.eventManager.subscribe('tagsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
