/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { VendorsDetailComponent } from 'app/entities/vendors/vendors-detail.component';
import { Vendors } from 'app/shared/model/vendors.model';

describe('Component Tests', () => {
  describe('Vendors Management Detail Component', () => {
    let comp: VendorsDetailComponent;
    let fixture: ComponentFixture<VendorsDetailComponent>;
    const route = ({ data: of({ vendors: new Vendors(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [VendorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VendorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vendors).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
