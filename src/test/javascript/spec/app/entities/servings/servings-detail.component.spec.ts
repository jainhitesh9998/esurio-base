/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { ServingsDetailComponent } from 'app/entities/servings/servings-detail.component';
import { Servings } from 'app/shared/model/servings.model';

describe('Component Tests', () => {
  describe('Servings Management Detail Component', () => {
    let comp: ServingsDetailComponent;
    let fixture: ComponentFixture<ServingsDetailComponent>;
    const route = ({ data: of({ servings: new Servings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [ServingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.servings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
