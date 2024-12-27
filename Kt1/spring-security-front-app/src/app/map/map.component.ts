import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';  // Import Leaflet biblioteku
import { BunnyPostService } from '../service/bunnyPost.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  // Korisničke koordinate (latitude, longitude)
  userLocation = {
    lat: 45.2671,  // Latitude for Novi Sad city center
    lng: 19.8335   // Longitude for Novi Sad city center
  };
  
  // List of posts with updated coordinates in Novi Sad
  posts: any[] = [];
  

  constructor(private bunnyPostService: BunnyPostService) { }

  ngOnInit(): void {
    this.loadLocations();
    //this.initializeMap();    
  }

  async loadLocations() {
    try {
      const data = await this.bunnyPostService.getBunnyPostLocations().toPromise();
      if (data) {
        this.posts = data;  // Assign the fetched posts to this.posts
        console.log("Posts data:", this.posts);
        this.initializeMap();  // Now initialize the map after data is loaded
      } else {
        console.error("Failed to load locations.");
      }
    } catch (error) {
      console.error("Error loading locations:", error);
    }
  }

  initializeMap(): void {
    // Kreiraj mapu i postavi je na lokaciju korisnika
    const map = L.map('map').setView([this.userLocation.lat, this.userLocation.lng], 13);

    // Dodaj tile layer za mapu (koristi OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Kreiraj prilagođenu ikonicu za korisnika
    const userIcon = L.icon({
        iconUrl: 'assets/icons/pin.png', // Zameni sa relativnom putanjom do ikonice
        iconSize: [48, 48],             // Prilagodi veličinu [širina, visina]
        iconAnchor: [16, 48],           // Tačka sidrišta (dno ikonice)
        popupAnchor: [0, -48]           // Tačka sidrišta za popup
    });

    const postIcon = L.icon({
      iconUrl: 'assets/icons/post.png', // Zameni sa relativnom putanjom do ikonice
      iconSize: [60, 38],             // Prilagodi veličinu [širina, visina]
      iconAnchor: [16, 48],           // Tačka sidrišta (dno ikonice)
      popupAnchor: [0, -48]           // Tačka sidrišta za popup
  });

    // Dodaj prilagođeni marker za lokaciju korisnika
    L.marker([this.userLocation.lat, this.userLocation.lng], { icon: userIcon })
      .addTo(map)
      .bindPopup("Your Location")
      .openPopup();

    // Dodaj markere za objave
    var i = 0;
    console.log("start locations");
    console.log("Posts data: ", JSON.stringify(this.posts));
    this.posts.forEach(post => {
      i+=1;
      console.log("location[" + i + "]: " + post.location.latitude + ", " + post.location.longitude + ", " + post.details);
      L.marker([post.location.latitude, post.location.longitude], { icon: postIcon })
        .addTo(map)
        .bindPopup(post.details)
        .openPopup();
    });
  }

}
