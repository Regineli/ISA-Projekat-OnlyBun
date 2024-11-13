import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BunnyPostFormComponent } from './bunny-post-form.component';


describe('BunnyPostFormComponent', () => {
  let component: BunnyPostFormComponent;
  let fixture: ComponentFixture<BunnyPostFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BunnyPostFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BunnyPostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
