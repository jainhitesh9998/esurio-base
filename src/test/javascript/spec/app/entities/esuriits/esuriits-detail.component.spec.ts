/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { EsuriitsDetailComponent } from 'app/entities/esuriits/esuriits-detail.component';
import { Esuriits } from 'app/shared/model/esuriits.model';

describe('Component Tests', () => {
  describe('Esuriits Management Detail Component', () => {
    let comp: EsuriitsDetailComponent;
    let fixture: ComponentFixture<EsuriitsDetailComponent>;
    const route = ({ data: of({ esuriits: new Esuriits(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [EsuriitsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EsuriitsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsuriitsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.esuriits).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
