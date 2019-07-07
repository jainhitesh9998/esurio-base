/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsurioTestModule } from '../../../test.module';
import { TagsComponent } from 'app/entities/tags/tags.component';
import { TagsService } from 'app/entities/tags/tags.service';
import { Tags } from 'app/shared/model/tags.model';

describe('Component Tests', () => {
  describe('Tags Management Component', () => {
    let comp: TagsComponent;
    let fixture: ComponentFixture<TagsComponent>;
    let service: TagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsurioTestModule],
        declarations: [TagsComponent],
        providers: []
      })
        .overrideTemplate(TagsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tags(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
