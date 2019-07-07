/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { DishesDetailComponent } from 'app/entities/dishes/dishes-detail.component';
import { Dishes } from 'app/shared/model/dishes.model';

describe('Component Tests', () => {
  describe('Dishes Management Detail Component', () => {
    let comp: DishesDetailComponent;
    let fixture: ComponentFixture<DishesDetailComponent>;
    const route = ({ data: of({ dishes: new Dishes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [DishesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DishesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DishesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dishes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
