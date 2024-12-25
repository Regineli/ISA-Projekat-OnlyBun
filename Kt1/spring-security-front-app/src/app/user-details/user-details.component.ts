import { Component, OnInit } from '@angular/core';
import {FooService} from '../service/foo.service';
import {UserService} from '../service/user.service';
import {ConfigService} from '../service/config.service';
import { BunnyPost, BunnyPostService } from '../service/bunnyPost.service';
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  bunnyPosts: BunnyPost[] = [];
  whoamIResponse = {};
  allUserResponse = {};
  currentUser!:any;
  pageUser!:any;
  username!: string;
  isEditing: boolean = false;
  isChangingPassword: boolean = false;
  passwordData = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  };
  errorMessage: string | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private config: ConfigService,
    private bunnyPostService: BunnyPostService,
    private userService: UserService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      this.username = params.get('username') || ''; // Get the username from the route parameter
      this.loadBunnyPosts(); // Load posts for the user
      this.loadUserDetails();
    });
    //this.loadBunnyPosts();
    this.currentUser = this.userService.currentUser;
    console.log("current user home: ", this.currentUser);
  }

  forgeResonseObj(obj:any, res:any, path:any) {
    obj['path'] = path;
    obj['method'] = 'GET';
    if (res.ok === false) {
      // err
      obj['status'] = res.status;
      try {
        obj['body'] = JSON.stringify(JSON.parse(res._body), null, 2);
      } catch (err) {
        console.log(res);
        obj['body'] = res.error.message;
      }
    } else {
      // 200
      obj['status'] = 200;
      obj['body'] = JSON.stringify(res, null, 2);
    }
  }

  loadBunnyPosts() {
    this.bunnyPostService.getBunnyPosts(this.username)
        .subscribe(res => {
          this.bunnyPosts = res;
          console.log("bunny post get res: ", res);
          console.log("bunny post get: ", this.bunnyPosts);
        }, err => {
          //this.forgeResonseObj(this.bunnyPosts, err, path);
          console.log("error getting bunny posts");
        });
  }

  loadUserDetails() {
    console.log('userDetails username: ' + this.username)
    this.userService.getUserDetails(this.username)
        .subscribe(res => {
          this.pageUser = res;
        }, err => {
          //this.forgeResonseObj(this.bunnyPosts, err, path);
          console.log("error getting bunny posts");
        });
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
    if (post) {
      post.showComments = !post.showComments; // Toggle visibility of comments
    }
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  navigateToUserDetails(username: string) {
    this.router.navigate(['/user-details', username]);  // Navigate to /user-details/:username
  }

  saveChanges() {
    // Call your function to save changes here
    
    delete this.pageUser.followers;
    delete this.pageUser.following;

    console.log('Changes saved:', this.pageUser);
    this.isEditing = false;
    this.isChangingPassword = false;
    // If changing password, also validate and process the password change
    if (this.passwordData.newPassword && this.passwordData.newPassword === this.passwordData.confirmPassword) {
      console.log('Password changed');
      // Implement your password change logic
    }
    var message = this.userService.updateUser(this.pageUser);
    console.log(message);
    
  }

  changePassword(){
    if (this.isChangingPassword){
      this.isChangingPassword = false;
    } else{
      this.isChangingPassword = true;
    }
  }
}
