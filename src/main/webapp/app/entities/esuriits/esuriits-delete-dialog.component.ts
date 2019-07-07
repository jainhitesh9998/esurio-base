import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEsuriits } from 'app/shared/model/esuriits.model';
import { EsuriitsService } from './esuriits.service';

@Component({
  selector: 'jhi-esuriits-delete-dialog',
  templateUrl: './esuriits-delete-dialog.component.html'
})
export class EsuriitsDeleteDialogComponent {
  esuriits: IEsuriits;

  constructor(protected esuriitsService: EsuriitsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.esuriitsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'esuriitsListModification',
        content: 'Deleted an esuriits'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-esuriits-delete-popup',
  template: ''
})
export class EsuriitsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esuriits }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EsuriitsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.esuriits = esuriits;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/esuriits', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/esuriits', { outlets: { popup: null } }]);
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
