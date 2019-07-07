import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutlets } from 'app/shared/model/outlets.model';

@Component({
  selector: 'jhi-outlets-detail',
  templateUrl: './outlets-detail.component.html'
})
export class OutletsDetailComponent implements OnInit {
  outlets: IOutlets;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ outlets }) => {
      this.outlets = outlets;
    });
  }

  previousState() {
    window.history.back();
  }
}
