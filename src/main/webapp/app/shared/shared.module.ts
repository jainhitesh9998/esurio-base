import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EsurioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [EsurioSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [EsurioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsurioSharedModule {
  static forRoot() {
    return {
      ngModule: EsurioSharedModule
    };
  }
}
