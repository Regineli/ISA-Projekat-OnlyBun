import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizacijaZaBriguComponent } from './organizacija-za-brigu.component';

describe('OrganizacijaZaBriguComponent', () => {
  let component: OrganizacijaZaBriguComponent;
  let fixture: ComponentFixture<OrganizacijaZaBriguComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizacijaZaBriguComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizacijaZaBriguComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
