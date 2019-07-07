/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { CentersDetailComponent } from 'app/entities/centers/centers-detail.component';
import { Centers } from 'app/shared/model/centers.model';

describe('Component Tests', () => {
  describe('Centers Management Detail Component', () => {
    let comp: CentersDetailComponent;
    let fixture: ComponentFixture<CentersDetailComponent>;
    const route = ({ data: of({ centers: new Centers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [CentersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CentersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CentersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.centers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
