import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MessageRequest } from '../model/message'; // import the DTO

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private apiUrl = 'http://localhost:8085/api/sendMessage';  // Adjust the URL to match your backend's endpoint

  constructor(private http: HttpClient) { }

  sendMessage(message: MessageRequest): Observable<string> {
    return this.http.post<string>(this.apiUrl, message);
  }
}
