/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EsurioTestModule } from '../../../test.module';
import { FoodcourtsDeleteDialogComponent } from 'app/entities/foodcourts/foodcourts-delete-dialog.component';
import { FoodcourtsService } from 'app/entities/foodcourts/foodcourts.service';

describe('Component Tests', () => {
  describe('Foodcourts Management Delete Component', () => {
    let comp: FoodcourtsDeleteDialogComponent;
    let fixture: ComponentFixture<FoodcourtsDeleteDialogComponent>;
    let service: FoodcourtsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [FoodcourtsDeleteDialogComponent]
      })
        .overrideTemplate(FoodcourtsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodcourtsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodcourtsService);
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
