import { Routes } from '@angular/router';
import { BunnyComponent } from './bunny/bunny.component'; // Adjust the path if needed



export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' }, //default route
    { path: 'bunny', component: BunnyComponent },
    { path: 'bunny-form', }
  // Add other routes here if necessary
];
