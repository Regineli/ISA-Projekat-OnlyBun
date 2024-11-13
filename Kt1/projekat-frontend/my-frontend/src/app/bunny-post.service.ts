import { Injectable } from '@angular/core';
import { BunnyPost } from './bunny-post/bunny-post.component';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BunnyPostService {

  private apiUrl = 'http://localhost:8080/api/bunnyPosts'; 


  constructor(private http: HttpClient) { }

  addOrUpdateBunnyPost(formData: FormData): Observable<BunnyPost> {
    return this.http.post<BunnyPost>(`${this.apiUrl}/add`, formData);
  }
}
