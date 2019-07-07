import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dishes } from 'app/shared/model/dishes.model';
import { DishesService } from './dishes.service';
import { DishesComponent } from './dishes.component';
import { DishesDetailComponent } from './dishes-detail.component';
import { DishesUpdateComponent } from './dishes-update.component';
import { DishesDeletePopupComponent } from './dishes-delete-dialog.component';
import { IDishes } from 'app/shared/model/dishes.model';

@Injectable({ providedIn: 'root' })
export class DishesResolve implements Resolve<IDishes> {
  constructor(private service: DishesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDishes> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Dishes>) => response.ok),
        map((dishes: HttpResponse<Dishes>) => dishes.body)
      );
    }
    return of(new Dishes());
  }
}

export const dishesRoute: Routes = [
  {
    path: '',
    component: DishesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DishesDetailComponent,
    resolve: {
      dishes: DishesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DishesUpdateComponent,
    resolve: {
      dishes: DishesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DishesUpdateComponent,
    resolve: {
      dishes: DishesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dishesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DishesDeletePopupComponent,
    resolve: {
      dishes: DishesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
