/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { AttendantsComponent } from 'app/entities/attendants/attendants.component';
import { AttendantsService } from 'app/entities/attendants/attendants.service';
import { Attendants } from 'app/shared/model/attendants.model';

describe('Component Tests', () => {
  describe('Attendants Management Component', () => {
    let comp: AttendantsComponent;
    let fixture: ComponentFixture<AttendantsComponent>;
    let service: AttendantsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [AttendantsComponent],
        providers: []
      })
        .overrideTemplate(AttendantsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendantsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendantsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Attendants(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
