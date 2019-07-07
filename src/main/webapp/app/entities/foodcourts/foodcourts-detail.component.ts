import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFoodcourts } from 'app/shared/model/foodcourts.model';

@Component({
  selector: 'jhi-foodcourts-detail',
  templateUrl: './foodcourts-detail.component.html'
})
export class FoodcourtsDetailComponent implements OnInit {
  foodcourts: IFoodcourts;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foodcourts }) => {
      this.foodcourts = foodcourts;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
