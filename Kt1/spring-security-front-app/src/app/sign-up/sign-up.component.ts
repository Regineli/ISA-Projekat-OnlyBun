import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, UserService } from '../service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

interface DisplayMessage {
  msgType: string;
  msgBody: string;
}

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit, OnDestroy {

  title = 'Sign up';
  form!: FormGroup;

  // Boolean for checking whether the form is submitted
  submitted = false;

  // Boolean to check if user is successfully registered
  userRegistered = false;

  // Notification message to display any messages (error/success)
  notification!: DisplayMessage;

  returnUrl!: string;
  private ngUnsubscribe: Subject<void> = new Subject<void>();

  // Constructor injecting necessary services
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    // Capture notification message from route parameters if available
    this.route.params
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((params: any) => {
        this.notification = params as DisplayMessage;
      });

    // Get the return URL from route query params or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    // Initialize the form with validation
    this.form = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(64)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(32)]],
      confirmPassword: ['', [Validators.required]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  // Custom validator for password match
  passwordMatchValidator(form: FormGroup) {
    if (form.get('password')?.value !== form.get('confirmPassword')?.value) {
      return { 'passwordMismatch': true };
    }
    return null;
  }

  // Handle form submission
  onSubmit() {
    this.submitted = true;

    // Check if the form is valid before submitting
    if (this.form.invalid) {
      return;
    }

    this.authService.signup(this.form.value)
      .subscribe(data => {
        console.log(data);
        // Automatically log the user in after successful sign up
        this.authService.login(this.form.value).subscribe(() => {
          this.userService.getMyInfo().subscribe();
        });
        
        // Set the userRegistered flag to true and show the success message
        this.userRegistered = true;
        
        // Navigate to the return URL
        this.router.navigate([this.returnUrl]);
      },
        error => {
          this.submitted = false;
          console.log('Sign up error', error);
          this.notification = { msgType: 'error', msgBody: error['error'].message };
        });
  }

  // Close success message (could be used to reset form or navigate elsewhere)
  close() {
    this.userRegistered = false;
    this.router.navigate(['/']); // or wherever you want to redirect the user after closing
  }
}
