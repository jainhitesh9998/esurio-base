import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Centers } from 'app/shared/model/centers.model';
import { CentersService } from './centers.service';
import { CentersComponent } from './centers.component';
import { CentersDetailComponent } from './centers-detail.component';
import { CentersUpdateComponent } from './centers-update.component';
import { CentersDeletePopupComponent } from './centers-delete-dialog.component';
import { ICenters } from 'app/shared/model/centers.model';

@Injectable({ providedIn: 'root' })
export class CentersResolve implements Resolve<ICenters> {
  constructor(private service: CentersService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICenters> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Centers>) => response.ok),
        map((centers: HttpResponse<Centers>) => centers.body)
      );
    }
    return of(new Centers());
  }
}

export const centersRoute: Routes = [
  {
    path: '',
    component: CentersComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Centers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CentersDetailComponent,
    resolve: {
      centers: CentersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Centers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CentersUpdateComponent,
    resolve: {
      centers: CentersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Centers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CentersUpdateComponent,
    resolve: {
      centers: CentersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Centers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const centersPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CentersDeletePopupComponent,
    resolve: {
      centers: CentersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Centers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
