import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendors } from 'app/shared/model/vendors.model';
import { VendorsService } from './vendors.service';

@Component({
  selector: 'jhi-vendors-delete-dialog',
  templateUrl: './vendors-delete-dialog.component.html'
})
export class VendorsDeleteDialogComponent {
  vendors: IVendors;

  constructor(protected vendorsService: VendorsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendorsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vendorsListModification',
        content: 'Deleted an vendors'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vendors-delete-popup',
  template: ''
})
export class VendorsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendors }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VendorsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vendors = vendors;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vendors', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vendors', { outlets: { popup: null } }]);
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
