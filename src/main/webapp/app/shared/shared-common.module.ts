import { NgModule } from '@angular/core';

import { EsurioSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [EsurioSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [EsurioSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EsurioSharedCommonModule {}
