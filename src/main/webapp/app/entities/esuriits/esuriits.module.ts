import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsurioSharedModule } from 'app/shared';
import {
  EsuriitsComponent,
  EsuriitsDetailComponent,
  EsuriitsUpdateComponent,
  EsuriitsDeletePopupComponent,
  EsuriitsDeleteDialogComponent,
  esuriitsRoute,
  esuriitsPopupRoute
} from './';

const ENTITY_STATES = [...esuriitsRoute, ...esuriitsPopupRoute];

@NgModule({
  imports: [EsurioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EsuriitsComponent,
    EsuriitsDetailComponent,
    EsuriitsUpdateComponent,
    EsuriitsDeleteDialogComponent,
    EsuriitsDeletePopupComponent
  ],
  entryComponents: [EsuriitsComponent, EsuriitsUpdateComponent, EsuriitsDeleteDialogComponent, EsuriitsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioEsuriitsModule {}
