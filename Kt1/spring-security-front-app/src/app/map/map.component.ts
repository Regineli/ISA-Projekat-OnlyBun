import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';  // Import Leaflet biblioteku

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  // Korisničke koordinate (latitude, longitude)
  userLocation = {
    lat: 44.8176,  // Primer latitude (možete postaviti pravu vrednost)
    lng: 20.4633   // Primer longitude (možete postaviti pravu vrednost)
  };

  // Lista objava sa koordinatama
  posts = [
    { id: 1, lat: 44.8180, lng: 20.4640, details: "Post 1" },
    { id: 2, lat: 44.8160, lng: 20.4620, details: "Post 2" },
    { id: 3, lat: 44.8190, lng: 20.4650, details: "Post 3" }
  ];

  constructor() { }

  ngOnInit(): void {
    this.initializeMap();
  }

  initializeMap(): void {
    // Kreiraj mapu i postavi je na lokaciju korisnika
    const map = L.map('map').setView([this.userLocation.lat, this.userLocation.lng], 13);

    // Dodaj tile layer za mapu (koristi OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Dodaj marker za korisnika
    L.marker([this.userLocation.lat, this.userLocation.lng])
      .addTo(map)
      .bindPopup("Your Location")
      .openPopup();

    // Dodaj markere za objave
    this.posts.forEach(post => {
      L.marker([post.lat, post.lng])
        .addTo(map)
        .bindPopup(post.details);
    });
  }
}
