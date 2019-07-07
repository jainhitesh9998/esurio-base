/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { CentersComponent } from 'app/entities/centers/centers.component';
import { CentersService } from 'app/entities/centers/centers.service';
import { Centers } from 'app/shared/model/centers.model';

describe('Component Tests', () => {
  describe('Centers Management Component', () => {
    let comp: CentersComponent;
    let fixture: ComponentFixture<CentersComponent>;
    let service: CentersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [CentersComponent],
        providers: []
      })
        .overrideTemplate(CentersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Centers(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.centers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
