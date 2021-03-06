import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITags } from 'app/shared/model/tags.model';
import { TagsService } from './tags.service';

@Component({
  selector: 'jhi-tags-delete-dialog',
  templateUrl: './tags-delete-dialog.component.html'
})
export class TagsDeleteDialogComponent {
  tags: ITags;

  constructor(protected tagsService: TagsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tagsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tagsListModification',
        content: 'Deleted an tags'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tags-delete-popup',
  template: ''
})
export class TagsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tags }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TagsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tags = tags;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tags', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tags', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
