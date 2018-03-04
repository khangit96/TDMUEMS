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
  dsCoQuanEmergency: CoQuan[] = [];
  coQuanEmergency: CoQuan;
  nhanVienSelected: NhanVien;
  check = false;
  loaiPhanAnh = "";

  constructor(private data: DataService, public db: AngularFireDatabase) { }

  ngOnInit() {
    this.data.currentSuKienPhanAnh.subscribe(
      skPhanAnh => (this.skPhanAnh = skPhanAnh)
    );

    this.getDSCoQuan();
    this.listenAddEmgergency();
  }

  showViTriTaiNan() { }

  getDSCoQuan() {
    let count = 0;
    const items = this.db.list("LoaiPhanAnhChinh", ref =>
      ref.orderByChild("ten").equalTo(this.skPhanAnh.loaiPhanAnh)
    );
    items.snapshotChanges().subscribe(actions => {
      if (!this.check) {
        console.log('dsCoQuan CHanged');
        this.dsCoQuan = [];
        actions.forEach(action => {
          this.loaiPhanAnh = action.key;
          let object = action.payload.val();
          this.dsCoQuan = object.coQuan;
          this.dsCoQuan.forEach(element => {
            element.key = String(count);
            count++;
            element.stt = count;
          });
        });
      }

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

  //Listen add emergency
  listenAddEmgergency() {
    let itemsRef = this.db.list("SuKienPhanAnh/" + this.skPhanAnh.key + "/Emergency");
    itemsRef.snapshotChanges(['child_added'])
      .subscribe(actions => {
        if (this.check) {
          console.log('add');
          let action = actions[actions.length - 1]
          let object = action.payload.val();
          this.coQuanEmergency = object;

          // Push emergency to agencies
          const items = this.db.object("LoaiPhanAnhChinh/" + this.loaiPhanAnh + "/coQuan/" + this.coQuanSelected.key + "/Emergency");
          items.set({ 'status': 'demo' });
          // console.log(object);
        }
        else {
          console.log('loading list');
        }

      });

    // let itemsRef = this.db.list('demo');
    // itemsRef.auditTrail()
    //   .subscribe(actions => {
    //     console.log('change');
    //     console.log('change');
    //     actions.forEach(action => {
    //       console.log(action.type);
    //       console.log(action.key);
    //       console.log(action.payload.val());
    //     });
    //   });
  }

  // Send Agency
  sendAgency(coQuan: CoQuan) {
    this.coQuanSelected = coQuan;
    this.check = true;
    let count = 0;
    const items = this.db.list("SuKienPhanAnh/" + this.skPhanAnh.key + "/Emergency");
    items.push(coQuan);

    let index = this.dsCoQuan.indexOf(coQuan);
    this.dsCoQuan.splice(index, 1);
    //alert(this.skPhanAnh.stt);
  }
}
