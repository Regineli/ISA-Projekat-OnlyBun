import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject = new BehaviorSubject<any>(null); // BehaviorSubject za trenutnog korisnika
  currentUser$ = this.currentUserSubject.asObservable(); // Observable za praćenje promena korisnika
  private callCount: number = 0;

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  setCurrentUser(user: any) {
    this.currentUserSubject.next(user);
  }

  // Dobijanje trenutnog korisnika
  get currentUser(): any {
    return this.currentUserSubject.value;
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url)
      .pipe(map(user => {
        console.log("get MY Info user: " + JSON.stringify(user));
        console.log("who am I url: " + this.config.whoami_url);
        this.setCurrentUser(user);
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

  getUsersTrending() {
    this.callCount += 1;  // Increment the call count

    console.log("callCount user: ", this.callCount);

    // Pass callCount as a query parameter
    const args = { testParam: this.callCount.toString() };
    return this.apiService.get(this.config.user_trending_url, args);
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
