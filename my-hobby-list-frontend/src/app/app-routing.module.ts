import { NgModule } from '@angular/core';
import routes from './app.routes';
import { RouteReuseStrategy, RouterModule } from '@angular/router';
import { CustomReuseStrategy } from './custom-reuse-estrategy';

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: "reload" })],
  exports: [RouterModule],
  providers: [
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}
  ],
})
export class AppRoutingModule { }
