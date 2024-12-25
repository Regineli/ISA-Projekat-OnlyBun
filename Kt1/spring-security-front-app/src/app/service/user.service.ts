import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!:any;

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url)
      .pipe(map(user => {
        console.log("get MY Info user: " + JSON.stringify(user));
        console.log("who am I url: " + this.config.whoami_url);
        this.currentUser = user;
        return user;
      }));
  }

  getUserDetails(username: string) {
    const path = `${this.config.user_details_url}`; // Base URL for the user details endpoint
    console.log("user service console log: " + path);
    // Pass the username as a query parameter or as part of the path
    const args = { username: username }; // Here, you are passing 'username' as a query parameter
    
    return this.apiService.get(path, args).pipe(
      map(user => {
        console.log("get user details: " + user);
        return user; // Return the user object
      })
    );
  }
  

  getAll() {
    return this.apiService.get(this.config.users_url);
  }

  updateUser(user: any): Observable<any> {
    const token = localStorage.getItem('jwtToken');  // Assuming the JWT token is saved in localStorage

    // Prepare the headers with the Bearer token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const path = `${this.config.update_user_url}`;
    console.log("update user path: " + path);
    // Call the post method with the user data and custom headers (token)
    return this.apiService.post(path , user);
  }

}
