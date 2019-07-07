import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendants } from 'app/shared/model/attendants.model';

@Component({
  selector: 'jhi-attendants-detail',
  templateUrl: './attendants-detail.component.html'
})
export class AttendantsDetailComponent implements OnInit {
  attendants: IAttendants;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendants }) => {
      this.attendants = attendants;
    });
  }

  previousState() {
    window.history.back();
  }
}
