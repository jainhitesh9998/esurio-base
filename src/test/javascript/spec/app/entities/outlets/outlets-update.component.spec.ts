/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { OutletsUpdateComponent } from 'app/entities/outlets/outlets-update.component';
import { OutletsService } from 'app/entities/outlets/outlets.service';
import { Outlets } from 'app/shared/model/outlets.model';

describe('Component Tests', () => {
  describe('Outlets Management Update Component', () => {
    let comp: OutletsUpdateComponent;
    let fixture: ComponentFixture<OutletsUpdateComponent>;
    let service: OutletsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [OutletsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OutletsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutletsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutletsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outlets(123);
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
        const entity = new Outlets();
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
