import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Menus } from 'app/shared/model/menus.model';
import { MenusService } from './menus.service';
import { MenusComponent } from './menus.component';
import { MenusDetailComponent } from './menus-detail.component';
import { MenusUpdateComponent } from './menus-update.component';
import { MenusDeletePopupComponent } from './menus-delete-dialog.component';
import { IMenus } from 'app/shared/model/menus.model';

@Injectable({ providedIn: 'root' })
export class MenusResolve implements Resolve<IMenus> {
  constructor(private service: MenusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMenus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Menus>) => response.ok),
        map((menus: HttpResponse<Menus>) => menus.body)
      );
    }
    return of(new Menus());
  }
}

export const menusRoute: Routes = [
  {
    path: '',
    component: MenusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MenusDetailComponent,
    resolve: {
      menus: MenusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MenusUpdateComponent,
    resolve: {
      menus: MenusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MenusUpdateComponent,
    resolve: {
      menus: MenusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const menusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MenusDeletePopupComponent,
    resolve: {
      menus: MenusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
