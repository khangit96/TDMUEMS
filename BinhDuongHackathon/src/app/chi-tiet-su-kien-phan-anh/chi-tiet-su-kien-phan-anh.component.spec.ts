/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ChiTietSuKienPhanAnhComponent } from './chi-tiet-su-kien-phan-anh.component';

describe('ChiTietSuKienPhanAnhComponent', () => {
  let component: ChiTietSuKienPhanAnhComponent;
  let fixture: ComponentFixture<ChiTietSuKienPhanAnhComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiTietSuKienPhanAnhComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiTietSuKienPhanAnhComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
