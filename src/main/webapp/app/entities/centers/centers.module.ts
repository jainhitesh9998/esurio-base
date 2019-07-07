import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  CentersComponent,
  CentersDetailComponent,
  CentersUpdateComponent,
  CentersDeletePopupComponent,
  CentersDeleteDialogComponent,
  centersRoute,
  centersPopupRoute
} from './';

const ENTITY_STATES = [...centersRoute, ...centersPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CentersComponent,
    CentersDetailComponent,
    CentersUpdateComponent,
    CentersDeleteDialogComponent,
    CentersDeletePopupComponent
  ],
  entryComponents: [CentersComponent, CentersUpdateComponent, CentersDeleteDialogComponent, CentersDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioCentersModule {}
