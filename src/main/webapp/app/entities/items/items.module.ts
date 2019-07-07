import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  ItemsComponent,
  ItemsDetailComponent,
  ItemsUpdateComponent,
  ItemsDeletePopupComponent,
  ItemsDeleteDialogComponent,
  itemsRoute,
  itemsPopupRoute
} from './';

const ENTITY_STATES = [...itemsRoute, ...itemsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ItemsComponent, ItemsDetailComponent, ItemsUpdateComponent, ItemsDeleteDialogComponent, ItemsDeletePopupComponent],
  entryComponents: [ItemsComponent, ItemsUpdateComponent, ItemsDeleteDialogComponent, ItemsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioItemsModule {}
