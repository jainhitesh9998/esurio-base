/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { VendorsComponent } from 'app/entities/vendors/vendors.component';
import { VendorsService } from 'app/entities/vendors/vendors.service';
import { Vendors } from 'app/shared/model/vendors.model';

describe('Component Tests', () => {
  describe('Vendors Management Component', () => {
    let comp: VendorsComponent;
    let fixture: ComponentFixture<VendorsComponent>;
    let service: VendorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [VendorsComponent],
        providers: []
      })
        .overrideTemplate(VendorsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendorsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vendors(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vendors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
