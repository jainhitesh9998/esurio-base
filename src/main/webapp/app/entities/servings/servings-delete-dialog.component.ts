import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServings } from 'app/shared/model/servings.model';
import { ServingsService } from './servings.service';

@Component({
  selector: 'jhi-servings-delete-dialog',
  templateUrl: './servings-delete-dialog.component.html'
})
export class ServingsDeleteDialogComponent {
  servings: IServings;

  constructor(protected servingsService: ServingsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.servingsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'servingsListModification',
        content: 'Deleted an servings'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-servings-delete-popup',
  template: ''
})
export class ServingsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ servings }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServingsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.servings = servings;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/servings', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/servings', { outlets: { popup: null } }]);
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
