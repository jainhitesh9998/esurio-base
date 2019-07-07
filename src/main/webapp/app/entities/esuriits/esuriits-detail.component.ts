import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEsuriits } from 'app/shared/model/esuriits.model';

@Component({
  selector: 'jhi-esuriits-detail',
  templateUrl: './esuriits-detail.component.html'
})
export class EsuriitsDetailComponent implements OnInit {
  esuriits: IEsuriits;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esuriits }) => {
      this.esuriits = esuriits;
    });
  }

  previousState() {
    window.history.back();
  }
}
