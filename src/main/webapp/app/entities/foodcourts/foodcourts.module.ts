import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  FoodcourtsComponent,
  FoodcourtsDetailComponent,
  FoodcourtsUpdateComponent,
  FoodcourtsDeletePopupComponent,
  FoodcourtsDeleteDialogComponent,
  foodcourtsRoute,
  foodcourtsPopupRoute
} from './';

const ENTITY_STATES = [...foodcourtsRoute, ...foodcourtsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FoodcourtsComponent,
    FoodcourtsDetailComponent,
    FoodcourtsUpdateComponent,
    FoodcourtsDeleteDialogComponent,
    FoodcourtsDeletePopupComponent
  ],
  entryComponents: [FoodcourtsComponent, FoodcourtsUpdateComponent, FoodcourtsDeleteDialogComponent, FoodcourtsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioFoodcourtsModule {}
