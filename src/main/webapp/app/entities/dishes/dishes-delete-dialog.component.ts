import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDishes } from 'app/shared/model/dishes.model';
import { DishesService } from './dishes.service';

@Component({
  selector: 'jhi-dishes-delete-dialog',
  templateUrl: './dishes-delete-dialog.component.html'
})
export class DishesDeleteDialogComponent {
  dishes: IDishes;

  constructor(protected dishesService: DishesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dishesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dishesListModification',
        content: 'Deleted an dishes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dishes-delete-popup',
  template: ''
})
export class DishesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dishes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DishesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dishes = dishes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dishes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dishes', { outlets: { popup: null } }]);
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
