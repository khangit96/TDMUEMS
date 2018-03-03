/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { HomNayComponent } from './hom-nay.component';

describe('HomNayComponent', () => {
  let component: HomNayComponent;
  let fixture: ComponentFixture<HomNayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomNayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomNayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
