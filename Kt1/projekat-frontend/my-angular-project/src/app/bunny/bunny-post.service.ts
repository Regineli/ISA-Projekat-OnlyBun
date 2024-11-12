import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

// Define the structure of the User object
interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
}

// Define the structure of your BunnyPost based on your API response
interface BunnyPost {
  id: number;
  details: string;  // Adjusted from 'title' to 'details' to match your API response
  photo: string;
  user: User; // Include the User object as part of BunnyPost
}

@Injectable({
  providedIn: 'root'
})
export class BunnyPostService {
  private apiUrl = 'http://localhost:8080/api/bunnyPosts';  // Define your API URL

  constructor(private http: HttpClient) {}  // Inject HttpClient

  // HTTP GET request to fetch bunny posts without returning anything
  getBunnyPosts(): void {
    console.log('Bunny Post Service Working');
    this.http.get<BunnyPost[]>(this.apiUrl).subscribe(
      (data) => {
        console.log('Fetched Bunny Posts:', data);  // Log the fetched data
      },
      (error) => {
        console.error('Error fetching bunny posts:', error);  // Log errors
      }
    );
  }

  // You can add more methods for posting, updating, or deleting bunny posts if needed
}
