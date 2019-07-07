/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EsurioTestModule } from '../../../test.module';
import { MenusDeleteDialogComponent } from 'app/entities/menus/menus-delete-dialog.component';
import { MenusService } from 'app/entities/menus/menus.service';

describe('Component Tests', () => {
  describe('Menus Management Delete Component', () => {
    let comp: MenusDeleteDialogComponent;
    let fixture: ComponentFixture<MenusDeleteDialogComponent>;
    let service: MenusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [MenusDeleteDialogComponent]
      })
        .overrideTemplate(MenusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MenusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenusService);
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
