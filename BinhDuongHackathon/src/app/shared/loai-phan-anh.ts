import { CoQuan } from "app/shared/co-quan";
import { NhanVien } from "app/shared/nhan-vien";


export class LoaiPhanAnh {
  public ten: string;
  public hinhAnh: string;
  public coQuan:CoQuan[];
  nhanVien:NhanVien[];
  constructor(ten: string, hinhAnh: string,coQuan:CoQuan[],nhanVien:NhanVien[]) {
    this.ten = ten;
    this.hinhAnh = hinhAnh;
    this.coQuan=coQuan;
    this.nhanVien=nhanVien;
  }
}
