import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { BunnyService } from './bunny.service';

describe('BunnyService', () => {
  let service: BunnyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BunnyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
