import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICenters } from 'app/shared/model/centers.model';

@Component({
  selector: 'jhi-centers-detail',
  templateUrl: './centers-detail.component.html'
})
export class CentersDetailComponent implements OnInit {
  centers: ICenters;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ centers }) => {
      this.centers = centers;
    });
  }

  previousState() {
    window.history.back();
  }
}
