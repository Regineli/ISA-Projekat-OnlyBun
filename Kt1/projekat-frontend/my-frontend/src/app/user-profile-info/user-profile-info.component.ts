import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UserService, User } from '../user.service'; // Import UserService


interface BunnyPost {
  id: number;
  details: string;
  photo: string;
  comment: { data: Comment[] }; // Comments are now part of the bunny post
  commentsVisible: boolean;
}

interface Comment {
  id: number;
  details: string;
  bunnyPostId: number;
  userId: number;
}

@Component({
  selector: 'app-user-profile-info',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './user-profile-info.component.html',
  styleUrl: './user-profile-info.component.css'
})




export class UserProfileInfoComponent implements OnInit {
  userDetails: any; 
   // This will hold the logged-in user's data
  bunnyPosts: BunnyPost[] = [];  // This will hold the user's bunny posts

  userId!: number;

  loggedUser: User | null = null;

  private apiUrl = 'http://localhost:8080/api/users/bunnyPosts';
  private apiUserDetails = 'http://localhost:8080/api/users';
  private apiPostComments = 'http://localhost:8080/api/bunnyPosts/comments';
  comments: Comment[] = [];

  constructor(private userService: UserService, private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Get the logged user from UserService
    this.userId = +this.route.snapshot.paramMap.get('userId')!;



    this.getUserDetails(this.userId);
    console.log("Logged user profile info: ", this.userDetails)
    // If the user is logged in, fetch bunny posts
    if (this.userDetails) {
      this.getUserBunnyPosts(this.userDetails.id);
    }


    this.userService.user$.subscribe((user) => {
      this.loggedUser = user; // Update the user when the data changes
      console.log("Logged user: ", this.loggedUser);
    });
  }

  // Function to get user bunny posts by user ID
  getUserBunnyPosts(userId: number): void {
    const params = new HttpParams().set('userID', userId.toString());

    // Directly call the API and assign the result to bunnyPosts
    this.http.get<any[]>(this.apiUrl, { params }).subscribe(
      (posts) => {
        this.bunnyPosts = posts;  // Assign the posts to bunnyPosts array
      },
      (error) => {
        console.error('Error fetching bunny posts:', error);
      }
    );
  }

  getUserDetails(userId: number): void {
    const url = `${this.apiUserDetails}/${userId}`;  // Fix the URL interpolation
    this.http.get<User>(url).subscribe(
      (userDetails) => {
        this.userDetails = userDetails;  // Assign the fetched user details to userDetails
        console.log("User profile details: ", this.userDetails);

        // Fetch bunny posts after user details are loaded
        this.getUserBunnyPosts(this.userDetails.id);
      },
      (error) => {
        console.error('Error fetching user details:', error);
      }
    );
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
  viewComments(postId: number): void {
    const post = this.bunnyPosts.find(p => p.id === postId);

    console.log("post", post)

    if (post) {
      post.commentsVisible = !post.commentsVisible; // Toggle visibility

      if (!post.comment) {
        post.comment = { data: [] };  // Initialize comment object if not already done
      }

      if (post.commentsVisible && post.comment.data.length === 0) {
        // Fetch comments if they haven't been loaded already
        this.getCommentsByBunnyPostId(postId).subscribe(
          (comments) => {
            post.comment.data = comments; // Attach the comments to the post
            console.log("post comments: ", post.comment.data)
          },
          (error) => {
            console.error('Error fetching comments:', error);
          }
        );
      }
    }
  }

  getCommentsByBunnyPostId(bunnyPostId: number): Observable<Comment[]> {
    const params = new HttpParams().set('bunnyPostId', bunnyPostId.toString());
    return this.http.get<Comment[]>(this.apiPostComments, { params });
  }

}
