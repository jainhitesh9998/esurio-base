/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { ServingsUpdateComponent } from 'app/entities/servings/servings-update.component';
import { ServingsService } from 'app/entities/servings/servings.service';
import { Servings } from 'app/shared/model/servings.model';

describe('Component Tests', () => {
  describe('Servings Management Update Component', () => {
    let comp: ServingsUpdateComponent;
    let fixture: ComponentFixture<ServingsUpdateComponent>;
    let service: ServingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [ServingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Servings(123);
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
        const entity = new Servings();
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
