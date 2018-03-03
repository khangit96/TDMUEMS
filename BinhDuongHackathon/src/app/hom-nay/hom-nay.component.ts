import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase, snapshotChanges } from 'angularfire2/database';
import { HoaDon } from 'app/shared/hoa-don';
import { GoiThucUong } from 'app/shared/goi-thuc-uong';
import { DataService } from 'app/shared/data.service';

@Component({
  selector: 'app-hom-nay',
  templateUrl: './hom-nay.component.html',
  styleUrls: ['./hom-nay.component.css']
})
export class HomNayComponent implements OnInit {

  constructor(public db: AngularFireDatabase, public data: DataService) { };
  date = new Date(Date.now());
  classDaThanhToan = 'w3-bar-item w3-button w3-red';
  classChuaThanhToan = 'w3-bar-item w3-button w3-green';
  displayDaThanhToan = true;
  displayChuaThanhToan = false;
  hoaDonDaThanhToan: HoaDon[] = [];
  hoaDonChuaThanhToan: HoaDon[] = [];
  danhSachGoiThucUongDaThanhToan: GoiThucUong[] = [];
  danhSachGoiThucUongChuaThanhToan: GoiThucUong[] = [];
  danSachGoiThucUongSelected: GoiThucUong[];
  keyHoaDonSelected: string;
  isThanhToan: boolean = false;
  tongSoTien = 0;
  banThanhToan = 0;
  hoadonSelected: HoaDon;

  ngOnInit() {
    // this.data.changeMessage('false');
    const hdDaThanhToan = this.db.list('/HoaDon',
      ref => ref.orderByChild('thoiGianNgan').equalTo(this.date.getDate() + '/' + Number(this.date.getMonth() + 1)));
    hdDaThanhToan.snapshotChanges()
      .subscribe(actions => {
        this.hoaDonDaThanhToan = [];
        this.hoaDonChuaThanhToan = [];
        this.tongSoTien = 0;
        let sttHoaDonDaThanhToan = 0;
        let sttHoaDonChuaThanhToan = 0;

        actions.forEach(action => {
          let objectHoaDon = action.payload.val();
          if (objectHoaDon.thanhToan) {
            objectHoaDon.danhSachGoiThucUong.forEach(element => {
              this.danhSachGoiThucUongDaThanhToan.push(new GoiThucUong(element.soLuong, element.soTien, element.gia, element.hinhAnh, element.ten));
            });
            this.hoaDonDaThanhToan.push(new HoaDon(sttHoaDonDaThanhToan, objectHoaDon.tenNhanVien, action.key, objectHoaDon.soBan, objectHoaDon.thanhToan, objectHoaDon.thoiGianNgan, objectHoaDon.thoiGianDai, objectHoaDon.tongSoTien, objectHoaDon.moTa, this.danhSachGoiThucUongDaThanhToan));
            this.danhSachGoiThucUongDaThanhToan = [];
            this.tongSoTien += objectHoaDon.tongSoTien;

          }
          else {
            objectHoaDon.danhSachGoiThucUong.forEach(element => {
              this.danhSachGoiThucUongChuaThanhToan.push(new GoiThucUong(element.soLuong, element.soTien, element.gia, element.hinhAnh, element.ten));
            });
            this.hoaDonChuaThanhToan.push(new HoaDon(sttHoaDonChuaThanhToan, objectHoaDon.tenNhanVien, action.key, objectHoaDon.soBan, objectHoaDon.thanhToan, objectHoaDon.thoiGianNgan, objectHoaDon.thoiGianDai, objectHoaDon.tongSoTien, objectHoaDon.moTa, this.danhSachGoiThucUongChuaThanhToan));
            this.danhSachGoiThucUongChuaThanhToan = [];
          }
        });
        this.hoaDonDaThanhToan.reverse();
        this.hoaDonChuaThanhToan.reverse();

        this.hoaDonDaThanhToan.forEach(element => {
          sttHoaDonDaThanhToan++;
          element.stt = sttHoaDonDaThanhToan;
        });

        this.hoaDonChuaThanhToan.forEach(element => {
          sttHoaDonChuaThanhToan++;
          element.stt = sttHoaDonChuaThanhToan;
        });

        //   this.data.changeMessage('true');
      });
  }

  clickChucNang(chucNang: string) {
    if (chucNang == 'DaThanhToan') {
      this.displayDaThanhToan = true;
      this.displayChuaThanhToan = false;

      this.classDaThanhToan = 'w3-bar-item w3-button w3-red';
      this.classChuaThanhToan = 'w3-bar-item w3-button w3-green'
    }
    else {
      this.displayChuaThanhToan = true;
      this.displayDaThanhToan = false;

      this.classChuaThanhToan = 'w3-bar-item w3-button w3-red';
      this.classDaThanhToan = 'w3-bar-item w3-button w3-green'
    }
  }

  xemChiTiet(hoaDon: HoaDon) {
    this.hoadonSelected = hoaDon;
    this.isThanhToan = hoaDon.thanhToan;
    this.danSachGoiThucUongSelected = hoaDon.danhSachGoiThucUong;
    this.keyHoaDonSelected = hoaDon.key;
    this.banThanhToan = hoaDon.soBan;
  }

  thanhToan() {
    const hoaDon = this.db.object('/HoaDon/' + this.keyHoaDonSelected);
    hoaDon.update({ thanhToan: true });

    const ban = this.db.list('/Ban', ref => ref.orderByChild('soBan').equalTo(this.banThanhToan));
    ban.snapshotChanges()
      .subscribe(actions => {
        this.db.object('/Ban/' + actions[0].key).update({ tinhTrang: true });
      })
  }

  inTatCa() {
    let printContents, popupWin;
    printContents = document.getElementById('inTatCa').innerHTML;
    popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
          <title>Print tab</title>
          <style>
          //........Customized style.......
          </style>
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
  }

  inHoaDon() {
    let printContents, popupWin;
    printContents = document.getElementById('inHoaDon').innerHTML;
    popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
          <title>Print tab</title>
          <style>
          //........Customized style.......
          </style>
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
  }
}
