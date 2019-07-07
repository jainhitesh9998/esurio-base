import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  CategoriesComponent,
  CategoriesDetailComponent,
  CategoriesUpdateComponent,
  CategoriesDeletePopupComponent,
  CategoriesDeleteDialogComponent,
  categoriesRoute,
  categoriesPopupRoute
} from './';

const ENTITY_STATES = [...categoriesRoute, ...categoriesPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategoriesComponent,
    CategoriesDetailComponent,
    CategoriesUpdateComponent,
    CategoriesDeleteDialogComponent,
    CategoriesDeletePopupComponent
  ],
  entryComponents: [CategoriesComponent, CategoriesUpdateComponent, CategoriesDeleteDialogComponent, CategoriesDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioCategoriesModule {}
