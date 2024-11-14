import { Component, OnInit } from '@angular/core';
import {FooService} from '../service/foo.service';
import {UserService} from '../service/user.service';
import {ConfigService} from '../service/config.service';
import { BunnyPost, BunnyPostService } from '../service/bunnyPost.service';

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

  constructor(
    private config: ConfigService,
    private bunnyPostService: BunnyPostService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.loadBunnyPosts();
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

}
