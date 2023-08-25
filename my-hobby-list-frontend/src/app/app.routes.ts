import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { LoginComponent } from "./pages/login/login.component";
import { RegistryComponent } from "./pages/registry/registry.component";
import { SearchComponent } from "./pages/search/search.component";
import { MediaDetailsComponent } from "./pages/media-details/media-details.component";
import { PageNotFoundComponent } from "./pages/page-not-found/page-not-found.component";
import { InsertComponent } from "./pages/insert/insert.component";
import { UserListComponent } from "./pages/user-list/user-list.component";
import { ProfileComponent } from "./pages/profile/profile.component";
import { SettingsPageComponent } from "./pages/settings-page/settings-page.component";
import { ChangeProfileImageComponent } from "./components/change-profile-image/change-profile-image.component";
import { ReviewsComponent } from "./components/reviews/reviews.component";

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "register", component: RegistryComponent},
  {path: "search", component: SearchComponent},
  {path: "media/:id", component: MediaDetailsComponent, children: [
    {path: "reviews", component: ReviewsComponent},
  ]},
  {path: "media/insert/:id", component: InsertComponent},
  {path: "list/:username", component: UserListComponent},
  {path: "profile/:username", component: ProfileComponent},
  {path: "settings", title: "Configurações", component: SettingsPageComponent, children: [
    {path: "change-profile-image", component: ChangeProfileImageComponent}
  ]},
  {path: '**', pathMatch: "full", component: PageNotFoundComponent}
];

export default routes;
