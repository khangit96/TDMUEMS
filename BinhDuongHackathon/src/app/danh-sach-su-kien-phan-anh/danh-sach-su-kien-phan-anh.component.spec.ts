/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DanhSachSuKienPhanAnhComponent } from './danh-sach-su-kien-phan-anh.component';

describe('DanhSachSuKienPhanAnhComponent', () => {
  let component: DanhSachSuKienPhanAnhComponent;
  let fixture: ComponentFixture<DanhSachSuKienPhanAnhComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DanhSachSuKienPhanAnhComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DanhSachSuKienPhanAnhComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
