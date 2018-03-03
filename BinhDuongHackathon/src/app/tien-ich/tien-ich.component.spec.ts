/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { TienIchComponent } from './tien-ich.component';

describe('TienIchComponent', () => {
  let component: TienIchComponent;
  let fixture: ComponentFixture<TienIchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TienIchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TienIchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
