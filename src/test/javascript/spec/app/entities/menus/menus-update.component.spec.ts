/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { MenusUpdateComponent } from 'app/entities/menus/menus-update.component';
import { MenusService } from 'app/entities/menus/menus.service';
import { Menus } from 'app/shared/model/menus.model';

describe('Component Tests', () => {
  describe('Menus Management Update Component', () => {
    let comp: MenusUpdateComponent;
    let fixture: ComponentFixture<MenusUpdateComponent>;
    let service: MenusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [MenusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MenusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MenusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Menus(123);
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
        const entity = new Menus();
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
