import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
  // Import DateTime from luxon or other date-time library

export interface User {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    username: string;
    address: string;
    status: string;
    roles: any[] | null;
  }
  
  export interface Comment {
    id: number;
    details: string;
    bunnyPostId: number; // ID of the BunnyPost this comment is related to
    userId: number; // ID of the user who commented
    user: User; // The user object of the commenter
}

export interface BunnyPost {
    id: number;
    details: string;
    photo: string;
    time: Date;
    user: User;
    comments: Comment[];
    showComments?: boolean;
}

@Injectable()
export class BunnyPostService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getBunnyPosts() {
    console.log("foo service foo url: ", this.config.bunny_post_url);
    return this.apiService.get(this.config.bunny_post_url);
  }

}
