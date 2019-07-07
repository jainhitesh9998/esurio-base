/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EsurioTestModule } from '../../../test.module';
import { CentersDeleteDialogComponent } from 'app/entities/centers/centers-delete-dialog.component';
import { CentersService } from 'app/entities/centers/centers.service';

describe('Component Tests', () => {
  describe('Centers Management Delete Component', () => {
    let comp: CentersDeleteDialogComponent;
    let fixture: ComponentFixture<CentersDeleteDialogComponent>;
    let service: CentersService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [CentersDeleteDialogComponent]
      })
        .overrideTemplate(CentersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CentersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentersService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
