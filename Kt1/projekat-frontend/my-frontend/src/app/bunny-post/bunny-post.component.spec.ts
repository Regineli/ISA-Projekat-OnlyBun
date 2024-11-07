import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BunnyPostComponent } from './bunny-post.component';

describe('BunnyPostComponent', () => {
  let component: BunnyPostComponent;
  let fixture: ComponentFixture<BunnyPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BunnyPostComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BunnyPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
