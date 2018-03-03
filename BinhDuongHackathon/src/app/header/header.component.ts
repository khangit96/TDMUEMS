import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase, snapshotChanges } from 'angularfire2/database';
import { HoaDon } from 'app/shared/hoa-don';
import { GoiThucUong } from 'app/shared/goi-thuc-uong';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  date = new Date(Date.now());
  hoaDon:HoaDon[]=[];
  constructor(public db: AngularFireDatabase) { }

  ngOnInit() {
    let dateFormat = new Date(this.date).getDate() + "/" + Number(new Date(this.date).getMonth() + 1);
    const hoaDon = this.db.list('/HoaDon',
      ref => ref.orderByChild('thoiGianNgan').equalTo(dateFormat).limitToLast(1));
    hoaDon.snapshotChanges(['child_added'])
      .subscribe(actions => {
          let objectHoaDon =actions[actions.length-1].payload.val();
          let danhSachThucUong:GoiThucUong[]=[];
           objectHoaDon.danhSachGoiThucUong.forEach(element => {
              danhSachThucUong.push(new GoiThucUong(element.soLuong, element.soTien, element.gia, element.hinhAnh, element.ten));
            });
            this.hoaDon.push(new HoaDon(1,objectHoaDon.tenNhanVien,actions[actions.length-1].key, objectHoaDon.soBan, objectHoaDon.thanhToan, objectHoaDon.thoiGianNgan, objectHoaDon.thoiGianDai, objectHoaDon.tongSoTien, objectHoaDon.moTa,danhSachThucUong));
            this.hoaDon.reverse();
          })
  }

 xemThongBao(){
   // this.hoaDon=[];
  }
}
