/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { FoodcourtsDetailComponent } from 'app/entities/foodcourts/foodcourts-detail.component';
import { Foodcourts } from 'app/shared/model/foodcourts.model';

describe('Component Tests', () => {
  describe('Foodcourts Management Detail Component', () => {
    let comp: FoodcourtsDetailComponent;
    let fixture: ComponentFixture<FoodcourtsDetailComponent>;
    const route = ({ data: of({ foodcourts: new Foodcourts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [FoodcourtsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FoodcourtsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodcourtsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foodcourts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
