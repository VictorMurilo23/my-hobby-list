import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { ErrorMessageComponent } from './components/error-message/error-message.component';
import { RegistryComponent } from './pages/registry/registry.component';
import { HomeComponent } from './pages/home/home.component';
import { MediaCardComponent } from './components/media-card/media-card.component';
import { HeaderComponent } from './components/header/header.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { SearchComponent } from './pages/search/search.component';
import { MediaDetailsComponent } from './pages/media-details/media-details.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent,
    ErrorMessageComponent,
    RegistryComponent,
    HomeComponent,
    MediaCardComponent,
    HeaderComponent,
    SearchBarComponent,
    SearchComponent,
    MediaDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {path: "", component: HomeComponent},
      {path: "login", component: LoginComponent},
      {path: "register", component: RegistryComponent},
      {path: "search", component: SearchComponent},
      {path: "media/:id", component: MediaDetailsComponent},
      {path: '**', component: PageNotFoundComponent}
    ]),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
