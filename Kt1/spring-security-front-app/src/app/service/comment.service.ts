import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';

@Injectable({
    providedIn: 'root' 
  })
export class CommentService {
    aaa: any;

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  addComment(data: any) {
    console.log("foo service foo url: ", this.config.add_comment_url);
    this.aaa=this.apiService.post(this.config.add_comment_url, data).subscribe(
        response => {
          console.log('Zahtev uspešno poslat:', response);
        },
        error => {
          console.error('Greška pri slanju zahteva:', error);
        }
      );      
  }
}
