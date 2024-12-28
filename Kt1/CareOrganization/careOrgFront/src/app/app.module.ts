import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';
import { OrganizacijaZaBriguComponent } from './organizacija-za-brigu/organizacija-za-brigu.component';

@NgModule({
  declarations: [
    AppComponent,  
    OrganizacijaZaBriguComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
