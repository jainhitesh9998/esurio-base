import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITags, Tags } from 'app/shared/model/tags.model';
import { TagsService } from './tags.service';

@Component({
  selector: 'jhi-tags-update',
  templateUrl: './tags-update.component.html'
})
export class TagsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]]
  });

  constructor(protected tagsService: TagsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tags }) => {
      this.updateForm(tags);
    });
  }

  updateForm(tags: ITags) {
    this.editForm.patchValue({
      id: tags.id,
      identifier: tags.identifier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tags = this.createFromForm();
    if (tags.id !== undefined) {
      this.subscribeToSaveResponse(this.tagsService.update(tags));
    } else {
      this.subscribeToSaveResponse(this.tagsService.create(tags));
    }
  }

  private createFromForm(): ITags {
    return {
      ...new Tags(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITags>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
