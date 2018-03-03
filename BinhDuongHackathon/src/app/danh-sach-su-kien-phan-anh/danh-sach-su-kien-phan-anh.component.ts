import { Component, OnInit } from "@angular/core";
import { AngularFireDatabase } from "angularfire2/database";
import { SuKienPhanAnh } from "app/shared/su-kien-phan-anh";
import { DataService } from "app/shared/data.service";
import { ApiAiService } from "app/shared/api-ai.service";

@Component({
  selector: "app-danh-sach-su-kien-phan-anh",
  templateUrl: "./danh-sach-su-kien-phan-anh.component.html",
  styleUrls: ["./danh-sach-su-kien-phan-anh.component.css"]
})
export class DanhSachSuKienPhanAnhComponent implements OnInit {
  date = new Date(Date.now());
  stt = 0;
  dsSuKienPhanAnh: SuKienPhanAnh[] = [];
  result: any;
  test = false;
  constructor(
    public db: AngularFireDatabase,
    public dataService: DataService,
    private chat: ApiAiService
  ) { }

  ngOnInit() {
    const items = this.db.list("SuKienPhanAnh");
    items.snapshotChanges().subscribe(actions => {
      this.dsSuKienPhanAnh = [];
      this.stt = 0;
      actions.forEach(action => {
        let objectSkPhanAnh = action.payload.val();

        let skPhanAnh = new SuKienPhanAnh(
          action.key,
          objectSkPhanAnh.kinhDo,
          objectSkPhanAnh.viDo,
          objectSkPhanAnh.thoiGian,
          objectSkPhanAnh.thoiGianNgan,
          objectSkPhanAnh.loaiPhanAnh,
          objectSkPhanAnh.moTa,
          objectSkPhanAnh.tinhTrang,
          objectSkPhanAnh.urlDownloadList,
          objectSkPhanAnh.viTri
        );
        // if (skPhanAnh.loaiPhanAnh == "") {
        //   this.chat.send("Kẹt xe tại đại học thủ dầu một").then(res => {
        //     this.result = res;
        //     this.result = this.result.result.fulfillment.speech;
        //     skPhanAnh.loaiPhanAnh=this.result;
        //   });
        // }
        this.dsSuKienPhanAnh.push(skPhanAnh);
      });
      this.dsSuKienPhanAnh.reverse();
      this.dsSuKienPhanAnh.forEach(element => {
        this.stt++;
        element.stt = this.stt;
      });
    });
  }

  chiTietSuKienPhanAnh(skPhanAnh: SuKienPhanAnh) {
    this.dataService.sendSuKienPhanAnh(skPhanAnh);
  }
  checkLoaiPhanAnh(loaiPhanAnh) {
    if (loaiPhanAnh == "Traffic") {
      return true;
    }
    return false;
  }
}
