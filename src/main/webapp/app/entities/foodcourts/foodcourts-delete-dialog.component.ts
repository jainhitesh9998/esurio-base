import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodcourts } from 'app/shared/model/foodcourts.model';
import { FoodcourtsService } from './foodcourts.service';

@Component({
  selector: 'jhi-foodcourts-delete-dialog',
  templateUrl: './foodcourts-delete-dialog.component.html'
})
export class FoodcourtsDeleteDialogComponent {
  foodcourts: IFoodcourts;

  constructor(
    protected foodcourtsService: FoodcourtsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.foodcourtsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'foodcourtsListModification',
        content: 'Deleted an foodcourts'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-foodcourts-delete-popup',
  template: ''
})
export class FoodcourtsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foodcourts }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FoodcourtsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.foodcourts = foodcourts;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/foodcourts', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/foodcourts', { outlets: { popup: null } }]);
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
