import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendants } from 'app/shared/model/attendants.model';
import { AttendantsService } from './attendants.service';

@Component({
  selector: 'jhi-attendants-delete-dialog',
  templateUrl: './attendants-delete-dialog.component.html'
})
export class AttendantsDeleteDialogComponent {
  attendants: IAttendants;

  constructor(
    protected attendantsService: AttendantsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.attendantsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'attendantsListModification',
        content: 'Deleted an attendants'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-attendants-delete-popup',
  template: ''
})
export class AttendantsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendants }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AttendantsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.attendants = attendants;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/attendants', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/attendants', { outlets: { popup: null } }]);
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
