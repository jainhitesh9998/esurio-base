import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vendors } from 'app/shared/model/vendors.model';
import { VendorsService } from './vendors.service';
import { VendorsComponent } from './vendors.component';
import { VendorsDetailComponent } from './vendors-detail.component';
import { VendorsUpdateComponent } from './vendors-update.component';
import { VendorsDeletePopupComponent } from './vendors-delete-dialog.component';
import { IVendors } from 'app/shared/model/vendors.model';

@Injectable({ providedIn: 'root' })
export class VendorsResolve implements Resolve<IVendors> {
  constructor(private service: VendorsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVendors> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vendors>) => response.ok),
        map((vendors: HttpResponse<Vendors>) => vendors.body)
      );
    }
    return of(new Vendors());
  }
}

export const vendorsRoute: Routes = [
  {
    path: '',
    component: VendorsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vendors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VendorsDetailComponent,
    resolve: {
      vendors: VendorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vendors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VendorsUpdateComponent,
    resolve: {
      vendors: VendorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vendors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VendorsUpdateComponent,
    resolve: {
      vendors: VendorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vendors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vendorsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VendorsDeletePopupComponent,
    resolve: {
      vendors: VendorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vendors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
