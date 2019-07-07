/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsurioTestModule } from '../../../test.module';
import { AttendantsDetailComponent } from 'app/entities/attendants/attendants-detail.component';
import { Attendants } from 'app/shared/model/attendants.model';

describe('Component Tests', () => {
  describe('Attendants Management Detail Component', () => {
    let comp: AttendantsDetailComponent;
    let fixture: ComponentFixture<AttendantsDetailComponent>;
    const route = ({ data: of({ attendants: new Attendants(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [AttendantsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttendantsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendantsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendants).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
