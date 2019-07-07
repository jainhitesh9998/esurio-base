import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Outlets } from 'app/shared/model/outlets.model';
import { OutletsService } from './outlets.service';
import { OutletsComponent } from './outlets.component';
import { OutletsDetailComponent } from './outlets-detail.component';
import { OutletsUpdateComponent } from './outlets-update.component';
import { OutletsDeletePopupComponent } from './outlets-delete-dialog.component';
import { IOutlets } from 'app/shared/model/outlets.model';

@Injectable({ providedIn: 'root' })
export class OutletsResolve implements Resolve<IOutlets> {
  constructor(private service: OutletsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOutlets> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Outlets>) => response.ok),
        map((outlets: HttpResponse<Outlets>) => outlets.body)
      );
    }
    return of(new Outlets());
  }
}

export const outletsRoute: Routes = [
  {
    path: '',
    component: OutletsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Outlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OutletsDetailComponent,
    resolve: {
      outlets: OutletsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Outlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OutletsUpdateComponent,
    resolve: {
      outlets: OutletsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Outlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OutletsUpdateComponent,
    resolve: {
      outlets: OutletsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Outlets'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const outletsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OutletsDeletePopupComponent,
    resolve: {
      outlets: OutletsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Outlets'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
