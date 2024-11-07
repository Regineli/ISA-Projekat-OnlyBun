import { Component, OnInit } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { UserService, User } from '../user.service'; // Import UserService


// Define the structure of your BunnyPost based on your API response
interface BunnyPost {
  id: number;
  details: string;
  photo: string;
  user: User;
}
@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit{
  constructor(private http: HttpClient, private router: Router, private userService: UserService) {}
    loggedUser: User | null = null;


  ngOnInit(): void { 
    this.getBunnyPost();
    this.userService.user$.subscribe((user) => {
      this.loggedUser = user; // Update the user when the data changes
      console.log("Logged user: ", this.loggedUser);
    });
  }



    bunnyPost: BunnyPost = {
      id: 0,
      details: '',
      photo: '',
      user: {
        id: 0,
        email: '',
        firstName: '',
        lastName: '',
        username: '',  // Added username
        password: '',  // Added password (if needed, you might consider excluding it from public interface)
        address: '' 
      },
    };
  
    bunnyPosts: BunnyPost[] = [];
    isDropdownOpen = false;
  
    
    toggleDropdown(){
      this.isDropdownOpen = !this.isDropdownOpen;
    }


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



    navigateToRegister(){
      this.router.navigate(['register']);
    }

    navigateToLogin(){
      this.router.navigate(['login']);
    }

    likePost(bunnyId: number) {
      console.log(`Liked bunny post with ID: ${bunnyId}`);
      // Add logic for liking a post (e.g., update the backend or frontend state)
    }
  
    // Function to comment on a bunny post
    commentPost(bunnyId: number) {
      console.log(`Commented on bunny post with ID: ${bunnyId}`);
      // Add logic for commenting on a post (e.g., open comment modal or page)
    }
  
    // Function to view comments for a bunny post
    viewComments(bunnyId: number) {
      console.log(`Viewing comments for bunny post with ID: ${bunnyId}`);
      // Add logic to display the comments for the selected post (e.g., navigate to a comments page)
    }

    viewUserDetails(userId: number | null): void {
      if (userId){
        console.log('User clicked:', userId);
        this.router.navigate([`/user_profile_info/${userId}`]);      
        // You can navigate to the user's profile page or perform other actions
        // For example, if you want to navigate to a user profile page:
        // this.router.navigate([`/user-profile/${userId}`]);
      } else{
        console.log("You are not logged in");
      }
    }

    logout(){
      this.userService.logout();
    }
}
