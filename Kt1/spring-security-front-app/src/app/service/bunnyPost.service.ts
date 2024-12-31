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
    likes: number;
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
  private callCount: number = 0;

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getBunnyPosts(username?: string) {
    console.log("foo service foo url: ", this.config.bunny_post_url);
    console.log("get bunny post username: " + username);
    const args = { username: username };
    return this.apiService.get(this.config.bunny_post_url, args);
  }

  getBunnyPostTrending() {
    this.callCount += 1;  // Increment the call count

    console.log("callCount bunny post: ", this.callCount);

    // Pass callCount as a query parameter
    const args = { testParam: this.callCount.toString() };  // Convert to string if needed
    return this.apiService.get(this.config.bunnyPost_trending_url, args);
}

  getBunnyPostLocations() {
    return this.apiService.get(this.config.bunnyPost_locations);
  }  

  getCareOrganizationMessages(){
    return this.apiService.get(this.config.care_org_messages_url);
  }
}
