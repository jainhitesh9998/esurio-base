/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { MenusDetailComponent } from 'app/entities/menus/menus-detail.component';
import { Menus } from 'app/shared/model/menus.model';

describe('Component Tests', () => {
  describe('Menus Management Detail Component', () => {
    let comp: MenusDetailComponent;
    let fixture: ComponentFixture<MenusDetailComponent>;
    const route = ({ data: of({ menus: new Menus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [MenusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MenusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MenusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.menus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
