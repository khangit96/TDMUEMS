import { Component, OnInit } from "@angular/core";
import { AngularFireDatabase } from "angularfire2/database";
import { LoaiPhanAnh } from "app/shared/loai-phan-anh";
import { DataService } from "app/shared/data.service";

@Component({
  selector: "app-main-sidebar",
  templateUrl: "./main-sidebar.component.html",
  styleUrls: ["./main-sidebar.component.css"]
})
export class MainSidebarComponent implements OnInit {
  danhSachLoaiPhanAnh: LoaiPhanAnh[] = [];
  constructor(
    public db: AngularFireDatabase,
    public dataService: DataService
  ) {}

  ngOnInit() {
    const items = this.db.list("LoaiPhanAnhChinh");
    items.snapshotChanges().subscribe(actions => {
      actions.forEach(action => {
        let objectLoaiPhanAnh = action.payload.val();
        let loaiPhanAnh = new LoaiPhanAnh(
          objectLoaiPhanAnh.ten,
          objectLoaiPhanAnh.hinhAnh,
          objectLoaiPhanAnh.coQuan,
          objectLoaiPhanAnh.NhanVien
        );

        if (loaiPhanAnh.ten != "Khác") {
          this.danhSachLoaiPhanAnh.push(loaiPhanAnh);
        }
      });
    });
  }

  chiTietLoai(loaiPhanAnh: LoaiPhanAnh) {
    let count = 0;
    loaiPhanAnh.coQuan.forEach(element => {
      count++;
      element["stt"] = count;
    });
    this.dataService.sendLoaiPhanAnh(loaiPhanAnh);
  }
}
