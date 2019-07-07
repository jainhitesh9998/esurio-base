import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'centers',
        loadChildren: './centers/centers.module#EsurioCentersModule'
      },
      {
        path: 'foodcourts',
        loadChildren: './foodcourts/foodcourts.module#EsurioFoodcourtsModule'
      },
      {
        path: 'esuriits',
        loadChildren: './esuriits/esuriits.module#EsurioEsuriitsModule'
      },
      {
        path: 'outlets',
        loadChildren: './outlets/outlets.module#EsurioOutletsModule'
      },
      {
        path: 'menus',
        loadChildren: './menus/menus.module#EsurioMenusModule'
      },
      {
        path: 'vendors',
        loadChildren: './vendors/vendors.module#EsurioVendorsModule'
      },
      {
        path: 'attendants',
        loadChildren: './attendants/attendants.module#EsurioAttendantsModule'
      },
      {
        path: 'items',
        loadChildren: './items/items.module#EsurioItemsModule'
      },
      {
        path: 'categories',
        loadChildren: './categories/categories.module#EsurioCategoriesModule'
      },
      {
        path: 'tags',
        loadChildren: './tags/tags.module#EsurioTagsModule'
      },
      {
        path: 'dishes',
        loadChildren: './dishes/dishes.module#EsurioDishesModule'
      },
      {
        path: 'orders',
        loadChildren: './orders/orders.module#EsurioOrdersModule'
      },
      {
        path: 'servings',
        loadChildren: './servings/servings.module#EsurioServingsModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioEntityModule {}
