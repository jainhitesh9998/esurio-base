import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMenus } from 'app/shared/model/menus.model';

@Component({
  selector: 'jhi-menus-detail',
  templateUrl: './menus-detail.component.html'
})
export class MenusDetailComponent implements OnInit {
  menus: IMenus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ menus }) => {
      this.menus = menus;
    });
  }

  previousState() {
    window.history.back();
  }
}
