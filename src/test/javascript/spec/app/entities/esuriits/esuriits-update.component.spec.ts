/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { EsuriitsUpdateComponent } from 'app/entities/esuriits/esuriits-update.component';
import { EsuriitsService } from 'app/entities/esuriits/esuriits.service';
import { Esuriits } from 'app/shared/model/esuriits.model';

describe('Component Tests', () => {
  describe('Esuriits Management Update Component', () => {
    let comp: EsuriitsUpdateComponent;
    let fixture: ComponentFixture<EsuriitsUpdateComponent>;
    let service: EsuriitsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [EsuriitsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EsuriitsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EsuriitsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsuriitsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Esuriits(123);
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
        const entity = new Esuriits();
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
