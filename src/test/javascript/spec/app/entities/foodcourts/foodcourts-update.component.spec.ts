/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { FoodcourtsUpdateComponent } from 'app/entities/foodcourts/foodcourts-update.component';
import { FoodcourtsService } from 'app/entities/foodcourts/foodcourts.service';
import { Foodcourts } from 'app/shared/model/foodcourts.model';

describe('Component Tests', () => {
  describe('Foodcourts Management Update Component', () => {
    let comp: FoodcourtsUpdateComponent;
    let fixture: ComponentFixture<FoodcourtsUpdateComponent>;
    let service: FoodcourtsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [FoodcourtsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FoodcourtsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodcourtsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodcourtsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Foodcourts(123);
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
        const entity = new Foodcourts();
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
