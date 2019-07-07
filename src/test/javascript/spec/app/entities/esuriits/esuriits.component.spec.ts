/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { EsuriitsComponent } from 'app/entities/esuriits/esuriits.component';
import { EsuriitsService } from 'app/entities/esuriits/esuriits.service';
import { Esuriits } from 'app/shared/model/esuriits.model';

describe('Component Tests', () => {
  describe('Esuriits Management Component', () => {
    let comp: EsuriitsComponent;
    let fixture: ComponentFixture<EsuriitsComponent>;
    let service: EsuriitsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [EsuriitsComponent],
        providers: []
      })
        .overrideTemplate(EsuriitsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EsuriitsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsuriitsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Esuriits(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.esuriits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
