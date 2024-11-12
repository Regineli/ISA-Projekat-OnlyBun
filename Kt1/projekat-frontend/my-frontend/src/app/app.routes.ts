import { Routes } from '@angular/router';
import { BunnyPostComponent } from './bunny-post/bunny-post.component';  // Import BunnyPostComponent
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginComponent } from './login/login.component';
import { UserProfileInfoComponent } from './user-profile-info/user-profile-info.component';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomePageComponent},
    { path: 'bunny-post', component: BunnyPostComponent},
    { path: 'login', component: LoginComponent},
    { path: 'user_profile_info/:userId', component: UserProfileInfoComponent },
    { path: 'register', component: RegisterComponent},
    { path: 'register-activation/:userId', component: LoginComponent }
];
