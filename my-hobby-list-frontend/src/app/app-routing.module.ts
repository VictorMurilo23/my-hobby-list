import { NgModule } from '@angular/core';
import routes from './app.routes';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: "reload" })],
  exports: [RouterModule],
  providers: [],
})
export class AppRoutingModule { }
