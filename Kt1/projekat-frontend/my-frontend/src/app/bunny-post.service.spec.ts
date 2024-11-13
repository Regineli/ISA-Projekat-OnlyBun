import { TestBed } from '@angular/core/testing';

import { BunnyPostService } from './bunny-post.service';

describe('BunnyPostService', () => {
  let service: BunnyPostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BunnyPostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
