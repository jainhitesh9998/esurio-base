import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  VendorsComponent,
  VendorsDetailComponent,
  VendorsUpdateComponent,
  VendorsDeletePopupComponent,
  VendorsDeleteDialogComponent,
  vendorsRoute,
  vendorsPopupRoute
} from './';

const ENTITY_STATES = [...vendorsRoute, ...vendorsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VendorsComponent,
    VendorsDetailComponent,
    VendorsUpdateComponent,
    VendorsDeleteDialogComponent,
    VendorsDeletePopupComponent
  ],
  entryComponents: [VendorsComponent, VendorsUpdateComponent, VendorsDeleteDialogComponent, VendorsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioVendorsModule {}
