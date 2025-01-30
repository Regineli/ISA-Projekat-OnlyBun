import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BunnPostComponent } from './bunn-post.component';

describe('BunnPostComponent', () => {
  let component: BunnPostComponent;
  let fixture: ComponentFixture<BunnPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BunnPostComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BunnPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
