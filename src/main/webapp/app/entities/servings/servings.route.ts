import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Servings } from 'app/shared/model/servings.model';
import { ServingsService } from './servings.service';
import { ServingsComponent } from './servings.component';
import { ServingsDetailComponent } from './servings-detail.component';
import { ServingsUpdateComponent } from './servings-update.component';
import { ServingsDeletePopupComponent } from './servings-delete-dialog.component';
import { IServings } from 'app/shared/model/servings.model';

@Injectable({ providedIn: 'root' })
export class ServingsResolve implements Resolve<IServings> {
  constructor(private service: ServingsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServings> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Servings>) => response.ok),
        map((servings: HttpResponse<Servings>) => servings.body)
      );
    }
    return of(new Servings());
  }
}

export const servingsRoute: Routes = [
  {
    path: '',
    component: ServingsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServingsDetailComponent,
    resolve: {
      servings: ServingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServingsUpdateComponent,
    resolve: {
      servings: ServingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServingsUpdateComponent,
    resolve: {
      servings: ServingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const servingsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServingsDeletePopupComponent,
    resolve: {
      servings: ServingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
