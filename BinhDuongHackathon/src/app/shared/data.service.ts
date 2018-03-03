import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { SuKienPhanAnh } from "app/shared/su-kien-phan-anh";
import { LoaiPhanAnh } from "app/shared/loai-phan-anh";
@Injectable()
export class DataService {
  private suKienPhanAnh = new BehaviorSubject<SuKienPhanAnh>(null);
  private loaiPhanAnh = new BehaviorSubject<LoaiPhanAnh>(null);

  currentSuKienPhanAnh = this.suKienPhanAnh.asObservable();
  currentLoaiPhanAnh = this.loaiPhanAnh.asObservable();

  constructor() {}

  sendSuKienPhanAnh(skPhanAnh: SuKienPhanAnh) {
    this.suKienPhanAnh.next(skPhanAnh);
  }

  sendLoaiPhanAnh(lPhanAnh: LoaiPhanAnh) {
    this.loaiPhanAnh.next(lPhanAnh);
  }
}
