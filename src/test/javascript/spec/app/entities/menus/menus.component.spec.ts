/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { MenusComponent } from 'app/entities/menus/menus.component';
import { MenusService } from 'app/entities/menus/menus.service';
import { Menus } from 'app/shared/model/menus.model';

describe('Component Tests', () => {
  describe('Menus Management Component', () => {
    let comp: MenusComponent;
    let fixture: ComponentFixture<MenusComponent>;
    let service: MenusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [MenusComponent],
        providers: []
      })
        .overrideTemplate(MenusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MenusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Menus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.menus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
