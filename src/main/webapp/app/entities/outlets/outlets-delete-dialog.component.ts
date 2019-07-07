import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutlets } from 'app/shared/model/outlets.model';
import { OutletsService } from './outlets.service';

@Component({
  selector: 'jhi-outlets-delete-dialog',
  templateUrl: './outlets-delete-dialog.component.html'
})
export class OutletsDeleteDialogComponent {
  outlets: IOutlets;

  constructor(protected outletsService: OutletsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.outletsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'outletsListModification',
        content: 'Deleted an outlets'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-outlets-delete-popup',
  template: ''
})
export class OutletsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ outlets }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OutletsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.outlets = outlets;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/outlets', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/outlets', { outlets: { popup: null } }]);
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
