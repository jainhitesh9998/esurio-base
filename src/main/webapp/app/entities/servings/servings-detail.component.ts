import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServings } from 'app/shared/model/servings.model';

@Component({
  selector: 'jhi-servings-detail',
  templateUrl: './servings-detail.component.html'
})
export class ServingsDetailComponent implements OnInit {
  servings: IServings;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ servings }) => {
      this.servings = servings;
    });
  }

  previousState() {
    window.history.back();
  }
}
