import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideRouter } from '@angular/router';  // Import provideRouter
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
<<<<<<< HEAD
import { routes } from './app/app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';  // Import your routing configuration
=======
import { routes } from './app/app.routes';  // Import your routing configuration
>>>>>>> d1075a4ed9b306fbffe54a9339eef175b841af5e

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),  // Enable fetch API for HttpClient
<<<<<<< HEAD
    provideRouter(routes), provideAnimationsAsync(),  // Provide routing configuration
=======
    provideRouter(routes),  // Provide routing configuration
>>>>>>> d1075a4ed9b306fbffe54a9339eef175b841af5e
  ]
})
  .catch((err) => console.error(err));
