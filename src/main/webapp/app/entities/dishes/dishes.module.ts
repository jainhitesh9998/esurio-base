import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  DishesComponent,
  DishesDetailComponent,
  DishesUpdateComponent,
  DishesDeletePopupComponent,
  DishesDeleteDialogComponent,
  dishesRoute,
  dishesPopupRoute
} from './';

const ENTITY_STATES = [...dishesRoute, ...dishesPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DishesComponent, DishesDetailComponent, DishesUpdateComponent, DishesDeleteDialogComponent, DishesDeletePopupComponent],
  entryComponents: [DishesComponent, DishesUpdateComponent, DishesDeleteDialogComponent, DishesDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioDishesModule {}
