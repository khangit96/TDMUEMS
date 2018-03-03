import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AngularFireDatabase } from 'angularfire2/database';

@Component({
  selector: 'app-dang-nhap',
  templateUrl: './dang-nhap.component.html',
  styleUrls: ['./dang-nhap.component.css']
})
export class DangNhapComponent implements OnInit {
  kiemTraDanghap=true;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  onSubmit(f: NgForm){
    if(f.value.tenDangNhap=='khangnhd'&&f.value.matKhau=='123'){
      this.router.navigateByUrl('/quan-tri/(quan-tri:danh-sach-su-kien-phan-anh)');
      this.kiemTraDanghap=true;
    }
    else{
      this.kiemTraDanghap=false;
    }



  }
}
