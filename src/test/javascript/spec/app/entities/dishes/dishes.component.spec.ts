/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { DishesComponent } from 'app/entities/dishes/dishes.component';
import { DishesService } from 'app/entities/dishes/dishes.service';
import { Dishes } from 'app/shared/model/dishes.model';

describe('Component Tests', () => {
  describe('Dishes Management Component', () => {
    let comp: DishesComponent;
    let fixture: ComponentFixture<DishesComponent>;
    let service: DishesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [DishesComponent],
        providers: []
      })
        .overrideTemplate(DishesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DishesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DishesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Dishes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dishes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
