// src/app/bunny.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { HttpClientModule } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class BunnyService {
  private apiUrl = 'http://localhost:8080/api/bunnyPosts';

  constructor() {
    console.log('Bunny Service constructor');
  }


  test_function(){
    console.log('Bunny Service Working');
  }
}