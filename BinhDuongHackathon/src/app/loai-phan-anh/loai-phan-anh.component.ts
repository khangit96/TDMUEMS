import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { RouterModule, Routes } from "@angular/router";
import { DataService } from "app/shared/data.service";
import { LoaiPhanAnh } from "app/shared/loai-phan-anh";

@Component({
  selector: "app-loai-phan-anh",
  templateUrl: "./loai-phan-anh.component.html",
  styleUrls: ["./loai-phan-anh.component.css"]
})
export class LoaiPhanAnhComponent implements OnInit {
  loaiPhanAnh: LoaiPhanAnh;
  constructor(private route: ActivatedRoute, private data: DataService) {}
  ngOnInit() {
    this.data.currentLoaiPhanAnh.subscribe(lPA => (this.loaiPhanAnh = lPA));
  }
}
