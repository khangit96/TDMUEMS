/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DangNhapComponent } from './dang-nhap.component';

describe('DangNhapComponent', () => {
  let component: DangNhapComponent;
  let fixture: ComponentFixture<DangNhapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DangNhapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DangNhapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
