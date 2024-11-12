// src/app/user.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';


// Define the structure of the User object
export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  username: string;  // Added username
  password: string;  // Added password (if needed, you might consider excluding it from public interface)
  address: string;   // Added address
}

@Injectable({
  providedIn: 'root', // This makes the service available globally
})
export class UserService {
  // Store user in a BehaviorSubject, which can emit values to subscribers
  private userSubject = new BehaviorSubject<User | null>(null);
  user$ = this.userSubject.asObservable(); // Observable for components to subscribe to

  constructor(private http: HttpClient) {}

  // Login function to call backend and authenticate user
  login(username: string, password: string) {
    const apiUrl = 'http://localhost:8080/auth/login'; // Your API endpoint
  
    // Prepare the credentials as a JSON object
    const credentials = { username, password };
  
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  
    // Make POST request with username and password as JSON
    return this.http.post<User>(apiUrl, credentials, { headers }).pipe(
      map(user => {
        // If user is returned, set the user in the service
        this.userSubject.next(user);
      }),
      catchError((error) => {
        // Handle error (invalid username/password or other errors)
        console.error('Login failed:', error);
        this.userSubject.next(null); // Reset user if login fails
        throw error; // Propagate the error
      })
    );
  }
  
  // Set the user manually (for example, after successful login)
  setUser(user: User | null): void {
    this.userSubject.next(user);
  }

  // Get the current user
  getUser(): User | null {
    return this.userSubject.value;
  }

  // Check if the user is logged in
  isLoggedIn(): boolean {
    return this.userSubject.value !== null;
  }

  register(user: User): Observable<any> {
    const apiRegisterUrl = 'http://localhost:8080/api/users/create';

    // Sending each parameter as a RequestParam in the URL
    const params = new URLSearchParams();
    params.append('email', user.email);
    params.append('username', user.username);
    params.append('password', user.password);
    params.append('firstName', user.firstName);
    params.append('lastName', user.lastName);
    params.append('address', user.address);

    return this.http.post<any>(`${apiRegisterUrl}?${params.toString()}`, null);
  }

  logout(): void {
    // Reset the user data
    this.userSubject.next(null);

    // Optional: clear any auth tokens from localStorage or sessionStorage
    //localStorage.removeItem('authToken'); // If you are using localStorage
    //sessionStorage.removeItem('authToken'); // If you are using sessionStorage

    // Optionally, if you're working with tokens, you might want to invalidate the token server-side
    // You can send a logout request to the backend to invalidate the token (e.g., API call).
  }
}
