import { Component } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

// Define the structure of the User object
interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
}


export interface BunnyPost {
  id: number;
  details: string;
  photo: string;
  user: User;
}

@Component({
  selector: 'app-bunny-post',
  standalone: true,  // Mark as standalone component
  templateUrl: './bunny-post.component.html',
  styleUrls: ['./bunny-post.component.css'],
  imports: [HttpClientModule, FormsModule, CommonModule], // Import HttpClientModule and FormsModule
})
export class BunnyPostComponent {
  bunnyPost: BunnyPost = {
    id: 0,
    details: '',
    photo: '',
    user: {
      id: 0,
      email: '',
      firstName: '',
      lastName: '',
    },
  };

  bunnyPosts: BunnyPost[] = [];

  constructor(private http: HttpClient) {}

  getBunnyPost() {
    const apiUrl = 'http://localhost:8080/api/bunnyPosts'; // Your API endpoint
  
    // Send GET request to the backend
    this.http.get<BunnyPost[]>(apiUrl).subscribe(
      (response) => {
        console.log('BunnyPosts fetched successfully:', response);
        this.bunnyPosts = response;
        console.log('BunnyPosts:', this.bunnyPosts);
        console.log('done!')
      },
      (error) => {
        console.error('Error fetching BunnyPosts:', error);
      }
    );
  }
}
