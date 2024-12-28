import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OrganizacijaZaBriguComponent } from './organizacija-za-brigu/organizacija-za-brigu.component';

const routes: Routes = [
  { path: '', component: OrganizacijaZaBriguComponent, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
