/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { ServingsComponent } from 'app/entities/servings/servings.component';
import { ServingsService } from 'app/entities/servings/servings.service';
import { Servings } from 'app/shared/model/servings.model';

describe('Component Tests', () => {
  describe('Servings Management Component', () => {
    let comp: ServingsComponent;
    let fixture: ComponentFixture<ServingsComponent>;
    let service: ServingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [ServingsComponent],
        providers: []
      })
        .overrideTemplate(ServingsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServingsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServingsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Servings(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.servings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
