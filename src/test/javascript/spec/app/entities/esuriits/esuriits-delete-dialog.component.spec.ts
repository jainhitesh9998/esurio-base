/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EsurioTestModule } from '../../../test.module';
import { EsuriitsDeleteDialogComponent } from 'app/entities/esuriits/esuriits-delete-dialog.component';
import { EsuriitsService } from 'app/entities/esuriits/esuriits.service';

describe('Component Tests', () => {
  describe('Esuriits Management Delete Component', () => {
    let comp: EsuriitsDeleteDialogComponent;
    let fixture: ComponentFixture<EsuriitsDeleteDialogComponent>;
    let service: EsuriitsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [EsuriitsDeleteDialogComponent]
      })
        .overrideTemplate(EsuriitsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsuriitsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsuriitsService);
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
