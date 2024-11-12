import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'my-angular-project';

  constructor(private router: Router) {}

  navigateToBunnyPost() {
    console.log('test bunny bunny');
    this.router.navigate(['bunny']); // Replace '...' with the actual route you want to navigate to
    console.log('test bunny bunny');
  }
}
