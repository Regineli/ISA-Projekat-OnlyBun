import { Component, OnInit } from '@angular/core';
import {FooService} from '../service/foo.service';
import {UserService} from '../service/user.service';
import {ConfigService} from '../service/config.service';
import { BunnyPost, BunnyPostService } from '../service/bunnyPost.service';
import { Router } from '@angular/router';  // Import the Router to navigate
import { MatDialog } from '@angular/material/dialog';
import { BunnPostComponent } from '../bunn-post/bunn-post.component';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommentService } from '../service/comment.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  bunnyPosts: BunnyPost[] = [];
  whoamIResponse = {};
  allUserResponse = {};
  currentUser!:any;
  role!: any;
  comment: string | undefined;
  commentsOnPost: any;

  commentForm = new FormGroup({
    comm: new FormControl('', [Validators.required])
  })
  

  constructor(
    private config: ConfigService,
    private bunnyPostService: BunnyPostService,
    public userService: UserService,
    private router: Router,
    private dialog: MatDialog,
    private commentService: CommentService
  ) {
  }

  ngOnInit() {
    this.loadBunnyPosts();    
    this.userService.currentUser$.subscribe((user) => {
      this.currentUser = user;
      console.log("current user home: ", this.currentUser);
      this.role=this.currentUser.roles[0];
    });

    
    this.currentUser = this.userService.currentUser;
    console.log("current user home: ", this.currentUser);
    
  }
/*
  makeRequest(path:any) {
    console.log("home component path: ", path);
    if (this.config.whoami_url.endsWith(path)) {
      this.userService.getMyInfo()
        .subscribe(res => {
          this.forgeResonseObj(this.whoamIResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.whoamIResponse, err, path);
        });
    } else {
      this.userService.getAll()
        .subscribe(res => {
          this.forgeResonseObj(this.allUserResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.allUserResponse, err, path);
        });
    }
  } */

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
    const path = '/api/bunnyPosts';
    this.bunnyPostService.getBunnyPosts()
        .subscribe(res => {
          this.bunnyPosts = res;
          console.log("bunny post get res: ", res);
          console.log("bunny post get: ", this.bunnyPosts);
        }, err => {
          //this.forgeResonseObj(this.bunnyPosts, err, path);
          console.log("error getting bunny posts");
        });
  }

  likePost(bunnyId: number) {
    console.log(`Liked bunny post with ID: ${bunnyId}`);
    this.userService.getUsersTrending().subscribe((data) => {
      console.log("user trending: " + JSON.stringify(data));
    });

    this.bunnyPostService.getBunnyPostTrending().subscribe((data) => {
      var x = data.totalBunnyPosts;
      console.log("total bunny posts: " + x);
      console.log("bunnyPost trending: " + JSON.stringify(data));
    });

    
    // Add logic for liking a post (e.g., update the backend or frontend state)
  }

  // Function to comment on a bunny post
  commentPost(bunnyId: number) {
    console.log(`Commented on bunny post with ID: ${bunnyId}`);
    if(this.commentForm.valid){
      this.comment= this.commentForm.value.comm ?? '';

      const commentData = {
        details: this.comment,
        bunnyPostId: bunnyId,
        userId: this.currentUser.id
      };
    
      const commentJson = JSON.stringify(commentData);
      this.commentService.addComment(commentJson);
      console.log(commentJson)
      this.commentForm.reset();
    }
    console.log(this.comment)
    
  }


  // Function to view comments for a bunny post
  viewComments(postId: number): void {
    const post = this.bunnyPosts.find(p => p.id === postId);
    if (post) {
      post.showComments = !post.showComments; // Toggle visibility
  
      if (post.showComments) { // Ako treba da se prikažu komentari
        this.bunnyPostService.getComments(postId).subscribe(
          (comments) => {
            this.commentsOnPost = comments; // Čuvamo komentare kada stignu
            console.log("Komentari učitani:", this.commentsOnPost);
          },
          (error) => {
            console.error("Greška pri učitavanju komentara:", error);
          }
        );
      }
    }
  }
  

  getCommentsOnPost(postId: number){
    console.log("qqqqqqqqqqqqqqqqqqqqqqqqqq")
    return this.commentsOnPost=this.bunnyPostService.getComments(postId);
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  navigateToUserDetails(username: string) {
    this.router.navigate(['/user-details', username]);  // Navigate to /user-details/:username
  }

  addPost(){
    console.log('aaaaaa')
    const dialogRef = this.dialog.open(BunnPostComponent, {
      width: '400px', 
      data: {}
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed', result);
      this.loadBunnyPosts();
    });
  }

  addForAdd(postId: number){
    console.log("loooog")
    console.log(postId)
    this.bunnyPostService.chosePostForAdd(postId).subscribe({
      next: (response) => {
        console.log('Post chosen for ad', response);
      },
      error: (error) => {
        console.error('Error', error);
      }
    });  
  }

  navigateToTrending(): void {
    this.router.navigate(['/trending']);
  }
}
