import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { User, UserService } from '../user.service';
import { BunnyPost } from '../bunny-post/bunny-post.component';
import { HttpClient } from '@angular/common/http';
import { BunnyPostService } from '../bunny-post.service';

@Component({
  selector: 'app-bunny-form',
  templateUrl: './bunny-post-form.component.html',
  standalone: true,
  styleUrls: ['./bunny-post-form.component.css'],
  imports:[
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
  ]
})
export class BunnyPostFormComponent implements OnInit {

  latitude: number = 0.0;
  longitude: number = 0.0;

  imageBase64: string="";


  isFormVisible: boolean = false;


  user?: User | null;
  nextId: number = 0;
  constructor( private userService: UserService, private bunnyPostService: BunnyPostService){}

  ngOnInit(): void {
    this.userService.user$.subscribe(user => {
      
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
      const formData = new FormData();

    formData.append('email', "sergej@nesto.com");  // Dodajte vrednost email-a
    formData.append('details', this.bunnyPostForm.value.description ?? '');  // Dodajte vrednost details
    formData.append('photo', this.bunnyPostForm.value.imageBase64 ?? '');  // Dodajte vrednost slike (ako postoji)
    formData.append('longitude', this.bunnyPostForm.value.longitude?.toString() ?? '');  // Dodajte vrednost longitude
    formData.append('latitude', this.bunnyPostForm.value.latitude?.toString() ?? '');  // Dodajte vrednost latitude
      
      this.bunnyPostService.addOrUpdateBunnyPost(formData).subscribe({
        next: (response) => {
          console.log('Post successfully added/updated', response);
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

