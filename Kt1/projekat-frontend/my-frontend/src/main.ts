import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideRouter } from '@angular/router';  // Import provideRouter
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';  // Import your routing configuration

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),  // Enable fetch API for HttpClient
    provideRouter(routes), provideAnimationsAsync(), provideAnimationsAsync(),  
  ]
})
  .catch((err) => console.error(err));
