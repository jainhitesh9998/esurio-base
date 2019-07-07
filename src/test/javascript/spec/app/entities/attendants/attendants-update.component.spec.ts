/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { AttendantsUpdateComponent } from 'app/entities/attendants/attendants-update.component';
import { AttendantsService } from 'app/entities/attendants/attendants.service';
import { Attendants } from 'app/shared/model/attendants.model';

describe('Component Tests', () => {
  describe('Attendants Management Update Component', () => {
    let comp: AttendantsUpdateComponent;
    let fixture: ComponentFixture<AttendantsUpdateComponent>;
    let service: AttendantsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [AttendantsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttendantsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendantsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendantsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Attendants(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Attendants();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
