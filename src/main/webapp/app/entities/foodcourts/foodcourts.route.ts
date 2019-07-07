import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Foodcourts } from 'app/shared/model/foodcourts.model';
import { FoodcourtsService } from './foodcourts.service';
import { FoodcourtsComponent } from './foodcourts.component';
import { FoodcourtsDetailComponent } from './foodcourts-detail.component';
import { FoodcourtsUpdateComponent } from './foodcourts-update.component';
import { FoodcourtsDeletePopupComponent } from './foodcourts-delete-dialog.component';
import { IFoodcourts } from 'app/shared/model/foodcourts.model';

@Injectable({ providedIn: 'root' })
export class FoodcourtsResolve implements Resolve<IFoodcourts> {
  constructor(private service: FoodcourtsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFoodcourts> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Foodcourts>) => response.ok),
        map((foodcourts: HttpResponse<Foodcourts>) => foodcourts.body)
      );
    }
    return of(new Foodcourts());
  }
}

export const foodcourtsRoute: Routes = [
  {
    path: '',
    component: FoodcourtsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Foodcourts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FoodcourtsDetailComponent,
    resolve: {
      foodcourts: FoodcourtsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Foodcourts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FoodcourtsUpdateComponent,
    resolve: {
      foodcourts: FoodcourtsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Foodcourts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FoodcourtsUpdateComponent,
    resolve: {
      foodcourts: FoodcourtsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Foodcourts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const foodcourtsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FoodcourtsDeletePopupComponent,
    resolve: {
      foodcourts: FoodcourtsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Foodcourts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
