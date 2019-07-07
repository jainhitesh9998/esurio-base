/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { DishesUpdateComponent } from 'app/entities/dishes/dishes-update.component';
import { DishesService } from 'app/entities/dishes/dishes.service';
import { Dishes } from 'app/shared/model/dishes.model';

describe('Component Tests', () => {
  describe('Dishes Management Update Component', () => {
    let comp: DishesUpdateComponent;
    let fixture: ComponentFixture<DishesUpdateComponent>;
    let service: DishesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [DishesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DishesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DishesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DishesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dishes(123);
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
        const entity = new Dishes();
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
