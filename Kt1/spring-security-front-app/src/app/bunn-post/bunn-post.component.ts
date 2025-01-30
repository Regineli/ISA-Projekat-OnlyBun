import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BunnyPost, BunnyPostRequest, BunnyPostService, User } from '../service/bunnyPost.service';
import { UserService } from '../service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-bunn',
  templateUrl: './bunn-post.component.html',
  standalone: true,
  styleUrls: ['./bunn-post.component.css'],
  imports:[
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
  ]
})
export class BunnPostComponent implements OnInit {

  latitude: number = 0.0;
  longitude: number = 0.0;

  imageBase64: string="";


  isFormVisible: boolean = false;


  user?: User | null;
  nextId: number = 0;
  constructor( 
    private userService: UserService, 
    private bunnyPostService: BunnyPostService,
    private dialog: MatDialog
  ){}

  ngOnInit(): void {
    this.userService.currentUser.subscribe((user: User | null | undefined) => {
      
      console.log(this.user?.email);
      this.user = user;
    });

  }
 
  bunnyPostForm = new FormGroup({
    longitude: new FormControl(0.0, [Validators.required]),
    latitude: new FormControl(0.0, [Validators.required]),
    description: new FormControl('', [Validators.required]),
    imageBase64: new FormControl('')
  })

  createPost(): void {  

    if (this.bunnyPostForm.valid) {
      const data: BunnyPostRequest = {
        email: "sergej@nesto.com",
        details: this.bunnyPostForm.value.description ?? '',
        photo: this.bunnyPostForm.value.imageBase64 ?? '',
        longitude: this.bunnyPostForm.value.longitude?.toString() ?? '',
        latitude: this.bunnyPostForm.value.latitude?.toString() ?? ''
      };
      

      this.bunnyPostService.addOrUpdateBunnyPost(data).subscribe({
        next: (response) => {
          console.log('Post successfully added/updated', response);
          this.dialog.closeAll();
        },
        error: (error) => {
          console.error('Error occurred while adding/updating post', error);
        }
      });  
    }
    
  }
  
 
  onFileSelected(event: any){
        const file:File = event.target.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            this.imageBase64 = reader.result as string;
            this.bunnyPostForm.patchValue({
              imageBase64: this.imageBase64
            });
        };
        reader.readAsDataURL(file); 
      }
}