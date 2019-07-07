import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  AttendantsComponent,
  AttendantsDetailComponent,
  AttendantsUpdateComponent,
  AttendantsDeletePopupComponent,
  AttendantsDeleteDialogComponent,
  attendantsRoute,
  attendantsPopupRoute
} from './';

const ENTITY_STATES = [...attendantsRoute, ...attendantsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AttendantsComponent,
    AttendantsDetailComponent,
    AttendantsUpdateComponent,
    AttendantsDeleteDialogComponent,
    AttendantsDeletePopupComponent
  ],
  entryComponents: [AttendantsComponent, AttendantsUpdateComponent, AttendantsDeleteDialogComponent, AttendantsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioAttendantsModule {}
