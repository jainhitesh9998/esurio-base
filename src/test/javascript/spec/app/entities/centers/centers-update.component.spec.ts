/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { CentersUpdateComponent } from 'app/entities/centers/centers-update.component';
import { CentersService } from 'app/entities/centers/centers.service';
import { Centers } from 'app/shared/model/centers.model';

describe('Component Tests', () => {
  describe('Centers Management Update Component', () => {
    let comp: CentersUpdateComponent;
    let fixture: ComponentFixture<CentersUpdateComponent>;
    let service: CentersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [CentersUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CentersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Centers(123);
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
        const entity = new Centers();
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
