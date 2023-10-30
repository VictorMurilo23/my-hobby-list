import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import routes from './app.routes';
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
import { InsertComponent } from './pages/insert/insert.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { CharactersComponent } from './components/characters/characters.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SettingsPageComponent } from './pages/settings-page/settings-page.component';
import { ChangeProfileImageComponent } from './components/change-profile-image/change-profile-image.component';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { CreateReviewComponent } from './components/create-review/create-review.component';
import { ReviewCardComponent } from './components/review-card/review-card.component';
import { UserReviewsPageComponent } from './pages/user-reviews-page/user-reviews-page.component';
import { ReviewCommentsComponent } from './pages/review-comments/review-comments.component';
import { PaginationComponent } from './components/pagination/pagination.component';

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
    MediaDetailsComponent,
    InsertComponent,
    UserListComponent,
    CharactersComponent,
    ProfileComponent,
    SettingsPageComponent,
    ChangeProfileImageComponent,
    ReviewsComponent,
    CreateReviewComponent,
    ReviewCardComponent,
    UserReviewsPageComponent,
    ReviewCommentsComponent,
    PaginationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
