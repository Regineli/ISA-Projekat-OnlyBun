import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService, User } from '../user.service'; // Adjust the import based on your service path
import { Router, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent implements OnInit {
  ngOnInit(): void {
      
  }

  registrationForm: FormGroup;
  passwordMatchError: boolean = false;
  errorMessage: string = '';
  userRegistered: boolean = false;

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) {
    this.registrationForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', [Validators.required]],
      address: ['', Validators.required]
    });
  }

  register(): void {
    // Reset password match error
    this.passwordMatchError = false;

    // Check if passwords match
    if (this.registrationForm.value.password !== this.registrationForm.value.confirmPassword) {
      this.passwordMatchError = true;
      return;
    }

    // Check if form is valid
    if (this.registrationForm.valid) {
      // Map form values to User object
      const user: User = {
        id: 0,  // id can be set to 0 or be omitted if not required at registration
        email: this.registrationForm.value.email,
        username: this.registrationForm.value.username,
        password: this.registrationForm.value.password,
        firstName: this.registrationForm.value.firstName,
        lastName: this.registrationForm.value.lastName,  // If last name is optional, leave it empty or add a field in the form
        address: this.registrationForm.value.address
      };

      console.log('User object:', user);

      // Call the registration service
      this.userService.register(user).subscribe(
        (response) => {
          console.log('User successfully registered:', response);
          this.userRegistered = true;
          // Redirect or show a success message
        },
        (error) => {
          console.error('Error registering user:', error);
          const firstKey = Object.keys(error.error)[0];
          this.errorMessage = error.error[firstKey];
          // Handle the error, e.g., show an error message to the user
        }
      );
    }
  }

  close(){
    this.router.navigate([`home`]); 
  }
}
