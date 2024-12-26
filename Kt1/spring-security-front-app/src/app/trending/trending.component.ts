import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {BunnyPostService, User} from '../service/bunnyPost.service';

@Component({
  selector: 'app-trending',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.css']
})
export class TrendingComponent implements OnInit{
  totalBunnyPosts!: number;
  bunnyPostsInLastMonth!: number;
  topBunnyPosts: any[] = [];
  topBunnyPostsInLastWeek: any[] = [];
  topUsers: any[] = [];

  selectedTab: number = 1;  // Default to show topBunnyPosts (Tab 1)

  constructor(private bunnyPostService: BunnyPostService, private userService: UserService) {}

  ngOnInit(): void {
    // Fetch trending bunny posts statistics (first 3 items)
    this.loadTrending();
  }


  loadTrending(){
    this.bunnyPostService.getBunnyPostTrending().subscribe((data) => {
      if (data) {
        this.totalBunnyPosts = data.totalBunnyPosts;
        this.bunnyPostsInLastMonth = data.bunnyPostsInLastMonth;
        this.topBunnyPosts = data.topBunnyPosts;
        this.topBunnyPostsInLastWeek = data.topBunnyPostsInLastWeek;        
      } else {
        console.log("No data received.");
      }
    });

    this.userService.getUsersTrending().subscribe((data) => {
      if (data) {
        this.topUsers = data;
      } else {
        console.log("No data received.");
      }
    });
  }

  // Method to switch between tabs
  selectTab(tabNumber: number): void {
    this.selectedTab = tabNumber;
  }
  
}
