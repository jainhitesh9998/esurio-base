import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDishes } from 'app/shared/model/dishes.model';

@Component({
  selector: 'jhi-dishes-detail',
  templateUrl: './dishes-detail.component.html'
})
export class DishesDetailComponent implements OnInit {
  dishes: IDishes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dishes }) => {
      this.dishes = dishes;
    });
  }

  previousState() {
    window.history.back();
  }
}
