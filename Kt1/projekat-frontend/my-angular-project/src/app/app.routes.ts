import { Routes } from '@angular/router';
import { BunnyComponent } from './bunny/bunny.component'; // Adjust the path if needed



export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' }, //default route
    { path: 'bunny', component: BunnyComponent },
<<<<<<< HEAD
    { path: 'bunny-form', }
=======
>>>>>>> d1075a4ed9b306fbffe54a9339eef175b841af5e
  // Add other routes here if necessary
];
