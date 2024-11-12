import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';  // <-- Import FormsModule here
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { UserService } from '../user.service'; // Import UserService

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  apiUrl: string = 'http://localhost:8080/api/users/login';
  isActivated = false;  // this is used when account is activated with email link
  activated_username: string = '';
  loginMessage: string = '';

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Check if the route contains a userId (coming from the register-activation route)
    this.route.paramMap.subscribe(params => {
      const register_confirmation_userId = params.get('userId');
      if (register_confirmation_userId) {
        // Trigger the account activation logic
        this.activateAccount(register_confirmation_userId);
      }
    });
  }

  logIn(): void {
    const email = this.email;
    const password = this.password;

    console.log('Credentials:', { email, password });

    // Call the login method from UserService
    this.userService.login(email, password).subscribe(
      (response) => {
        console.log('Login successful', response);

        // After successful login, navigate to the dashboard or home page
        this.router.navigate(['/home']);
      },
      (error) => {
        console.log("Login message: ")
      const firstKey = Object.keys(error.error)[0];
      this.loginMessage = error.error.message;
      console.log(this.loginMessage);
        console.error('Login failed', error);
        // Handle error (e.g., display error message to user)
      }
    );
  }

  activateAccount(userId: string): void {
    fetch(`http://localhost:8080/api/users/activate?userId=${userId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log('Account activated successfully', data);
      // Optionally, redirect the user to login page after activation
      // this.router.navigate(['/login']);
      this.activated_username = data.username;
      this.isActivated = true;
    })
    .catch(error => {      
      console.error('Error activating account:', error);
      alert('Error activating account. Please try again later.');
    });
  }

  closeMessageBox(): void {
    this.isActivated = false;  // Hide the message box when the close button is clicked
  }
  
}
