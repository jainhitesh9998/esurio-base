import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  ServingsComponent,
  ServingsDetailComponent,
  ServingsUpdateComponent,
  ServingsDeletePopupComponent,
  ServingsDeleteDialogComponent,
  servingsRoute,
  servingsPopupRoute
} from './';

const ENTITY_STATES = [...servingsRoute, ...servingsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServingsComponent,
    ServingsDetailComponent,
    ServingsUpdateComponent,
    ServingsDeleteDialogComponent,
    ServingsDeletePopupComponent
  ],
  entryComponents: [ServingsComponent, ServingsUpdateComponent, ServingsDeleteDialogComponent, ServingsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioServingsModule {}
