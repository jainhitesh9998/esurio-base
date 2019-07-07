/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { OutletsDetailComponent } from 'app/entities/outlets/outlets-detail.component';
import { Outlets } from 'app/shared/model/outlets.model';

describe('Component Tests', () => {
  describe('Outlets Management Detail Component', () => {
    let comp: OutletsDetailComponent;
    let fixture: ComponentFixture<OutletsDetailComponent>;
    const route = ({ data: of({ outlets: new Outlets(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [OutletsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OutletsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutletsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outlets).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
