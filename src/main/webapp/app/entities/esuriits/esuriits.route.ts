import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Esuriits } from 'app/shared/model/esuriits.model';
import { EsuriitsService } from './esuriits.service';
import { EsuriitsComponent } from './esuriits.component';
import { EsuriitsDetailComponent } from './esuriits-detail.component';
import { EsuriitsUpdateComponent } from './esuriits-update.component';
import { EsuriitsDeletePopupComponent } from './esuriits-delete-dialog.component';
import { IEsuriits } from 'app/shared/model/esuriits.model';

@Injectable({ providedIn: 'root' })
export class EsuriitsResolve implements Resolve<IEsuriits> {
  constructor(private service: EsuriitsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEsuriits> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Esuriits>) => response.ok),
        map((esuriits: HttpResponse<Esuriits>) => esuriits.body)
      );
    }
    return of(new Esuriits());
  }
}

export const esuriitsRoute: Routes = [
  {
    path: '',
    component: EsuriitsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Esuriits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EsuriitsDetailComponent,
    resolve: {
      esuriits: EsuriitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Esuriits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EsuriitsUpdateComponent,
    resolve: {
      esuriits: EsuriitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Esuriits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EsuriitsUpdateComponent,
    resolve: {
      esuriits: EsuriitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Esuriits'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const esuriitsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EsuriitsDeletePopupComponent,
    resolve: {
      esuriits: EsuriitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Esuriits'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
