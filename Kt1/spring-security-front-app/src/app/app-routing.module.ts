import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { TrendingComponent } from './trending/trending.component';
import { MapComponent } from './map/map.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'signup',
    component: SignUpComponent,
  },
  {
    path: 'user-details/:username',
    component: UserDetailsComponent,
  },
  {
    path: 'trending',
    component: TrendingComponent,
  },
  {
    path: 'map',
    component: MapComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
