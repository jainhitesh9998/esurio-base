import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  MenusComponent,
  MenusDetailComponent,
  MenusUpdateComponent,
  MenusDeletePopupComponent,
  MenusDeleteDialogComponent,
  menusRoute,
  menusPopupRoute
} from './';

const ENTITY_STATES = [...menusRoute, ...menusPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MenusComponent, MenusDetailComponent, MenusUpdateComponent, MenusDeleteDialogComponent, MenusDeletePopupComponent],
  entryComponents: [MenusComponent, MenusUpdateComponent, MenusDeleteDialogComponent, MenusDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioMenusModule {}
