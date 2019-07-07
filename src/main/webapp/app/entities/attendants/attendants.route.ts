import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Attendants } from 'app/shared/model/attendants.model';
import { AttendantsService } from './attendants.service';
import { AttendantsComponent } from './attendants.component';
import { AttendantsDetailComponent } from './attendants-detail.component';
import { AttendantsUpdateComponent } from './attendants-update.component';
import { AttendantsDeletePopupComponent } from './attendants-delete-dialog.component';
import { IAttendants } from 'app/shared/model/attendants.model';

@Injectable({ providedIn: 'root' })
export class AttendantsResolve implements Resolve<IAttendants> {
  constructor(private service: AttendantsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendants> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Attendants>) => response.ok),
        map((attendants: HttpResponse<Attendants>) => attendants.body)
      );
    }
    return of(new Attendants());
  }
}

export const attendantsRoute: Routes = [
  {
    path: '',
    component: AttendantsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Attendants'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttendantsDetailComponent,
    resolve: {
      attendants: AttendantsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Attendants'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttendantsUpdateComponent,
    resolve: {
      attendants: AttendantsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Attendants'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttendantsUpdateComponent,
    resolve: {
      attendants: AttendantsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Attendants'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const attendantsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AttendantsDeletePopupComponent,
    resolve: {
      attendants: AttendantsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Attendants'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
