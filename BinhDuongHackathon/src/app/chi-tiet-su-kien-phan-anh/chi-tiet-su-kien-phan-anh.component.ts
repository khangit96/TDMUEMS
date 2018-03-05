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
    } else if (content == "HinhAnh") {
      this.displayMoTa = false;
      this.displayHinhAnhVaVideo = true;
      this.displayBanDo = false;

      this.tabMoTa = "w3-bar-item w3-button tablink w3-green";
      this.tabHinhAnh = "w3-bar-item w3-button tablink w3-red";
      this.tabBanDo = "w3-bar-item w3-button tablink w3-green";
    } else {
      this.displayMoTa = false;
      this.displayHinhAnhVaVideo = false;
      this.displayBanDo = true;

      this.tabMoTa = "w3-bar-item w3-button tablink w3-green";
      this.tabHinhAnh = "w3-bar-item w3-button tablink w3-green";
      this.tabBanDo = "w3-bar-item w3-button tablink w3-red";
    }
  }

  //Listen add emergency
  listenAddEmgergency() {
    let itemsRef = this.db.list("SuKienPhanAnh/" + this.skPhanAnh.key + "/Emergency");
    itemsRef.snapshotChanges()
      .subscribe(actions => {
        this.dsCoQuanEmergency=[];
        if (this.check) {

          let action = actions[actions.length - 1];
          if (action.type == 'child_added') {
            let object = action.payload.val();
            this.coQuanSelected = object;
            this.coQuanSelected.keyEmergency = action.key;
            console.log(this.coQuanSelected);

            // // Push emergency to agencies
            // const items = this.db.object("LoaiPhanAnhChinh/" + this.loaiPhanAnh + "/coQuan/" + this.coQuanSelected.key + "/Emergency");
            // items.set({ 'status': 'demo' });
            actions.forEach(action => {
              let object = action.payload.val();
              this.dsCoQuanEmergency.push(object);
            });
          }

          else if (action.type == 'child_changed') {
            actions.forEach(action => {
              let object = action.payload.val();
              this.dsCoQuanEmergency.push(object);
            });
          }
        }

        else {
          actions.forEach(action => {
            let object = action.payload.val();
            this.dsCoQuanEmergency.push(object);
          });
        }
        console.log(this.dsCoQuanEmergency.length);

      });
  }

  // Send Agency
  sendAgency(coQuan: CoQuan) {
    this.coQuanSelected = coQuan;
    this.coQuanSelected.keyEvent = this.skPhanAnh.key;
    this.coQuanSelected.status='Pending';
    this.coQuanSelected.latitudeEmergency=this.skPhanAnh.viDo;
    this.coQuanSelected.longtitudeEmergency=this.skPhanAnh.kinhDo;
    this.coQuanSelected.addressEmergency=this.skPhanAnh.viTri;
    this.coQuanSelected.descriptionEymergency=this.skPhanAnh.moTa;
    
    this.check = true;
    let count = 0;
    const items = this.db.list("SuKienPhanAnh/" + this.skPhanAnh.key + "/Emergency");
    items.push(coQuan);

    let index = this.dsCoQuan.indexOf(coQuan);
    this.dsCoQuan.splice(index, 1);
  }
}
