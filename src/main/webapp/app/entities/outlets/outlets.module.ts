import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  OutletsComponent,
  OutletsDetailComponent,
  OutletsUpdateComponent,
  OutletsDeletePopupComponent,
  OutletsDeleteDialogComponent,
  outletsRoute,
  outletsPopupRoute
} from './';

const ENTITY_STATES = [...outletsRoute, ...outletsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OutletsComponent,
    OutletsDetailComponent,
    OutletsUpdateComponent,
    OutletsDeleteDialogComponent,
    OutletsDeletePopupComponent
  ],
  entryComponents: [OutletsComponent, OutletsUpdateComponent, OutletsDeleteDialogComponent, OutletsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioOutletsModule {}
