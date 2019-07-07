import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICenters } from 'app/shared/model/centers.model';
import { CentersService } from './centers.service';

@Component({
  selector: 'jhi-centers-delete-dialog',
  templateUrl: './centers-delete-dialog.component.html'
})
export class CentersDeleteDialogComponent {
  centers: ICenters;

  constructor(protected centersService: CentersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.centersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'centersListModification',
        content: 'Deleted an centers'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-centers-delete-popup',
  template: ''
})
export class CentersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ centers }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CentersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.centers = centers;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/centers', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/centers', { outlets: { popup: null } }]);
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
