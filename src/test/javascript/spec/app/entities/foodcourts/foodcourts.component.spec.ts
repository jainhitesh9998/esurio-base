/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { FoodcourtsComponent } from 'app/entities/foodcourts/foodcourts.component';
import { FoodcourtsService } from 'app/entities/foodcourts/foodcourts.service';
import { Foodcourts } from 'app/shared/model/foodcourts.model';

describe('Component Tests', () => {
  describe('Foodcourts Management Component', () => {
    let comp: FoodcourtsComponent;
    let fixture: ComponentFixture<FoodcourtsComponent>;
    let service: FoodcourtsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [FoodcourtsComponent],
        providers: []
      })
        .overrideTemplate(FoodcourtsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodcourtsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodcourtsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Foodcourts(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.foodcourts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
