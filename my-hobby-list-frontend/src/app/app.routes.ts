import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { LoginComponent } from "./pages/login/login.component";
import { RegistryComponent } from "./pages/registry/registry.component";
import { SearchComponent } from "./pages/search/search.component";
import { MediaDetailsComponent } from "./pages/media-details/media-details.component";
import { PageNotFoundComponent } from "./pages/page-not-found/page-not-found.component";
import { InsertComponent } from "./pages/insert/insert.component";

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "register", component: RegistryComponent},
  {path: "search", component: SearchComponent},
  {path: "media/:id", component: MediaDetailsComponent},
  {path: "media/insert/:id", component: InsertComponent},
  {path: '**', component: PageNotFoundComponent}
];

export default routes;
