import { Component } from '@angular/core';
import { MessageService } from '../services/messageQueue.service';
import { MessageRequest } from '../model/message';

@Component({
  selector: 'app-organizacija-za-brigu',
  templateUrl: './organizacija-za-brigu.component.html',
  styleUrls: ['./organizacija-za-brigu.component.css']
})
export class OrganizacijaZaBriguComponent {
  naziv: string = '';
  latitude: number = 0;
  longitude: number = 0;

  constructor(private messageService: MessageService) {}

  onSubmit() {
    const message: MessageRequest = {
      naziv: this.naziv,
      latitude: this.latitude,
      longitude: this.longitude
    };

    this.messageService.sendMessage(message).subscribe(response => {
      console.log(response);  // Handle success
    }, error => {
      console.error('Error sending message:', error);  // Handle error
    });
  }
}
