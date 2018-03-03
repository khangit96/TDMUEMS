import { Component, OnInit } from "@angular/core";
import { DataService } from "app/shared/data.service";
import { SuKienPhanAnh } from "app/shared/su-kien-phan-anh";
import { AngularFireDatabase } from "angularfire2/database";
import { CoQuan } from "app/shared/co-quan";
import { LoaiPhanAnh } from "app/shared/loai-phan-anh";
import { NhanVien } from "app/shared/nhan-vien";

@Component({
  selector: "app-chi-tiet-su-kien-phan-anh",
  templateUrl: "./chi-tiet-su-kien-phan-anh.component.html",
  styleUrls: ["./chi-tiet-su-kien-phan-anh.component.css"]
})
export class ChiTietSuKienPhanAnhComponent implements OnInit {
  skPhanAnh: SuKienPhanAnh;
  urlImg: string[] = [];
  urlVid: string[] = [];
  zoom = 13;
  tenHanhVi = "";
  dsCoQuan: CoQuan[] = [];
  displayMoTa = true;
  displayHinhAnhVaVideo = false;
  displayBanDo = false;
  test = false;
  showCoQuan = false;
  iconPhanAnhURL = "https://i.imgur.com/fYnnrna.png";
  iconCoQuan = "https://i.imgur.com/bdt8jh0.png";
  iconCongAn = "https://i.imgur.com/WxRE2Tk.png";

  tabMoTa = "w3-bar-item w3-button tablink w3-red";
  tabHinhAnh = "w3-bar-item w3-button tablink w3-green";
  tabBanDo = "w3-bar-item w3-button tablink w3-green";

  coQuanSelected: CoQuan;
  textButton = "Gửi yêu cầu";
  dsNhanVien: NhanVien[] = [];
  nhanVienSelected: NhanVien;
  constructor(private data: DataService, public db: AngularFireDatabase) { }

  ngOnInit() {
    this.data.currentSuKienPhanAnh.subscribe(
      skPhanAnh => (this.skPhanAnh = skPhanAnh)
    );
    // this.skPhanAnh.dsUrlData.forEach(element => {
    //   if (element.type == "image/jpeg") {
    //     this.urlImg.push(element.urlDownload);
    //   } else {
    //     this.urlVid.push(element.urlDownload);
    //   }
    // });

    this.getDSCoQuan();
    // this.getLoaiPhanAnhFromFirebase();
  }
  // showMaker(coQuan: CoQuan) {
  //   this.coQuanSelected = coQuan;
  //   this.showCoQuan = true;
  // }

  showViTriTaiNan() { }

  getLoaiPhanAnhFromFirebase() {
    const items = this.db.list("LoaiPhanAnhChinh", ref =>
      ref.orderByChild("ten").equalTo(this.skPhanAnh.loaiPhanAnh)
    );
    items.snapshotChanges().subscribe(actions => {
      actions.forEach(action => {
        let object = action.payload.val();
        let loaiPhanAnh = new LoaiPhanAnh(
          object.ten,
          object.hinhAnh,
          object.coQuan,
          object.NhanVien
        );
        this.dsCoQuan = loaiPhanAnh.coQuan;
        this.dsNhanVien = loaiPhanAnh.nhanVien;
      });
    });
  }

  getDSCoQuan() {
    let count = 0;
    const items = this.db.list("LoaiPhanAnhChinh", ref =>
      ref.orderByChild("ten").equalTo(this.skPhanAnh.loaiPhanAnh)
    );
    items.snapshotChanges().subscribe(actions => {
      actions.forEach(action => {

        let object = action.payload.val();
        // let loaiPhanAnh = new LoaiPhanAnh(
        //   object.ten,
        //   object.hinhAnh,
        //   object.coQuan,
        //   object.NhanVien
        // );
        // this.dsCoQuan = loaiPhanAnh.coQuan;
        // this.dsNhanVien = loaiPhanAnh.nhanVien;
        this.dsCoQuan = object.coQuan;
        this.dsCoQuan.forEach(element => {
          count++;
          element.stt = count;
        });
      });
    });
  }
  SendNhanVien() {
    this.textButton = "Đang chờ xác nhận";
    this.db.list("NhanVien").push({
      moTa: this.skPhanAnh.moTa,
      thoiGian: this.skPhanAnh.thoiGian,
      viTri: this.skPhanAnh.viTri,
      viDo: this.skPhanAnh.viDo,
      kinhDo: this.skPhanAnh.kinhDo,
      diaChi: this.nhanVienSelected.diaChi
    });

    // let index = this.dsNhanVien.indexOf(this.nhanVienSelected);
    // this.dsNhanVien.splice(index, 1);
  }
  showNhanVien(nhanVien: NhanVien) {
    this.nhanVienSelected = nhanVien;
  }
  clickChucNang(content: string) {
    if (content == "MoTa") {
      this.displayMoTa = true;
      this.displayHinhAnhVaVideo = false;
      this.displayBanDo = false;

      this.tabMoTa = "w3-bar-item w3-button tablink w3-red";
      this.tabHinhAnh = "w3-bar-item w3-button tablink w3-green";
      this.tabBanDo = "w3-bar-item w3-button tablink w3-green";
      this.test = false;
    } else if (content == "HinhAnh") {
      this.displayMoTa = false;
      this.displayHinhAnhVaVideo = true;
      this.displayBanDo = false;

      this.tabMoTa = "w3-bar-item w3-button tablink w3-green";
      this.tabHinhAnh = "w3-bar-item w3-button tablink w3-red";
      this.tabBanDo = "w3-bar-item w3-button tablink w3-green";
      this.test = false;
    } else {
      this.displayMoTa = false;
      this.displayHinhAnhVaVideo = false;
      this.displayBanDo = true;

      this.tabMoTa = "w3-bar-item w3-button tablink w3-green";
      this.tabHinhAnh = "w3-bar-item w3-button tablink w3-green";
      this.tabBanDo = "w3-bar-item w3-button tablink w3-red";
      this.test = true;
    }
  }
}
