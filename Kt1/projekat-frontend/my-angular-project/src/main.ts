import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';  // Import HttpClient and withFetch
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

// Modify appConfig to include HttpClient with fetch enabled
bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    provideHttpClient(withFetch())  // Enable fetch API for HttpClient
  ]
})
  .catch((err) => console.error(err));
