import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';

@Injectable()
export class FooService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getFoo() {
    console.log("foo service foo url: ", this.config.bunny_post_url);
    return this.apiService.get(this.config.bunny_post_url);
  }

}
