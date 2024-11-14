import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8080/api';
  private _auth_url = 'http://localhost:8080/auth';
  private _user_url = this._api_url + '/user';

  private _login_url = this._auth_url + '/login';

  get login_url(): string {
    return this._login_url;
  }

  private _whoami_url = this._api_url + '/users/whoami';

  get whoami_url(): string {
    return this._whoami_url;
  }

  private _users_url = this._user_url + '/all';

  get users_url(): string {
    return this._users_url;
  }

  private _bunny_post_url = this._api_url + '/bunnyPosts/public';

  get bunny_post_url(): string {
    console.log("foo url: ", this._bunny_post_url);
    return this._bunny_post_url;
  }

  private _signup_url = this._auth_url + '/signup';

  get signup_url(): string {
    return this._signup_url;
  }
  private _comment_url = this._api_url + '/comments/public';

  get comment_url(): string {
    console.log("comment url: ", this._comment_url);
    return this._comment_url;
  }


}
