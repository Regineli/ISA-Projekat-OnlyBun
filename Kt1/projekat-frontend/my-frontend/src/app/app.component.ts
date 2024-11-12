import { Component } from '@angular/core';
import { BunnyPostComponent } from './bunny-post/bunny-post.component';  // Import BunnyPost component
import { RouterOutlet, Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [RouterOutlet]  // Enables the router outlet for navigation
})
export class AppComponent {
  title = "main page";

  constructor(
    private router: Router,  // Inject Router for navigation
  ) {}

  // Method to navigate to Bunny Posts route and fetch the posts
  navigateAndFetchBunnyPosts() {
    this.router.navigate(['bunny-posts']);  // Navigate to bunny-posts route
  }
}
