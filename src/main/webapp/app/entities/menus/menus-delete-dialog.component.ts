import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMenus } from 'app/shared/model/menus.model';
import { MenusService } from './menus.service';

@Component({
  selector: 'jhi-menus-delete-dialog',
  templateUrl: './menus-delete-dialog.component.html'
})
export class MenusDeleteDialogComponent {
  menus: IMenus;

  constructor(protected menusService: MenusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.menusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'menusListModification',
        content: 'Deleted an menus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-menus-delete-popup',
  template: ''
})
export class MenusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ menus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MenusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.menus = menus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/menus', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/menus', { outlets: { popup: null } }]);
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
