import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BunnyPostService } from '../service/bunnyPost.service';

@Component({
  selector: 'app-bunny-post-form',
  templateUrl: './bunny-post-form.component.html',
  styleUrls: ['./bunny-post-form.component.css']
})
export class BunnyPostFormComponent {

  latitude: number = 0.0;
  longitude: number = 0.0;

  imageBase64: string="";


  isFormVisible: boolean = false;

  constructor(private bunnyPostService: BunnyPostService){}


  bunnyPostForm = new FormGroup({
    longitude: new FormControl(0.0, [Validators.required]),
    latitude: new FormControl(0.0, [Validators.required]),
    description: new FormControl('', [Validators.required]),
    imageBase64: new FormControl('')
  })

  createPost(): void {  

    if (this.bunnyPostForm.valid) {
      const formData = new FormData();

      const body = {
        email: "sergej@nesto.com", // Vrednost za email
        details: this.bunnyPostForm.value.description ?? '', // Vrednost za details
        photo: this.bunnyPostForm.value.imageBase64 ?? '', // Vrednost slike (ako postoji)
        longitude: this.bunnyPostForm.value.longitude?.toString() ?? '', // Vrednost longitude
        latitude: this.bunnyPostForm.value.latitude?.toString() ?? '' // Vrednost latitude
      };
      

    formData.append('email', "sergej@nesto.com");  // Dodajte vrednost email-a
    formData.append('details', this.bunnyPostForm.value.description ?? '');  // Dodajte vrednost details
    formData.append('photo', this.bunnyPostForm.value.imageBase64 ?? '');  // Dodajte vrednost slike (ako postoji)
    formData.append('longitude', this.bunnyPostForm.value.longitude?.toString() ?? '');  // Dodajte vrednost longitude
    formData.append('latitude', this.bunnyPostForm.value.latitude?.toString() ?? '');  // Dodajte vrednost latitude
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });
      this.bunnyPostService.addBunnyPost(JSON.stringify(body)).subscribe({
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
