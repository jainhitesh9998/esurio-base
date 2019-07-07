import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendors } from 'app/shared/model/vendors.model';

@Component({
  selector: 'jhi-vendors-detail',
  templateUrl: './vendors-detail.component.html'
})
export class VendorsDetailComponent implements OnInit {
  vendors: IVendors;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendors }) => {
      this.vendors = vendors;
    });
  }

  previousState() {
    window.history.back();
  }
}
