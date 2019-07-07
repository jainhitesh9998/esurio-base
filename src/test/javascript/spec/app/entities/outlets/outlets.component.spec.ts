/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { OutletsComponent } from 'app/entities/outlets/outlets.component';
import { OutletsService } from 'app/entities/outlets/outlets.service';
import { Outlets } from 'app/shared/model/outlets.model';

describe('Component Tests', () => {
  describe('Outlets Management Component', () => {
    let comp: OutletsComponent;
    let fixture: ComponentFixture<OutletsComponent>;
    let service: OutletsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [OutletsComponent],
        providers: []
      })
        .overrideTemplate(OutletsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutletsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutletsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Outlets(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.outlets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
