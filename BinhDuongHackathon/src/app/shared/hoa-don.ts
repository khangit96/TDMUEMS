import { GoiThucUong } from "app/shared/goi-thuc-uong";

export class HoaDon {
  public stt:number;
  public key:string;
  public soBan:number;
  public thanhToan:boolean;
  public thoiGianNgan:string;
  public thoiGianDai:string
  public tongSoTien:number;
  public moTa:string;
  public tenNhanVien:string;
  public danhSachGoiThucUong:GoiThucUong[];
  constructor(stt:number,tenNhanVien:string,key:string,soBan:number,thanhToan:boolean,thoiGianNgan:string,thoiGianDai:string,tongSoTien:number,moTa:string,danhSachGoiThucUong:GoiThucUong[]){
    this.stt=stt;
    this.key=key;
    this.tenNhanVien=tenNhanVien;
    this.soBan=soBan;
    this.thanhToan=thanhToan;
    this.thoiGianNgan=thoiGianNgan;
    this.thoiGianDai=thoiGianDai;
    this.tongSoTien=tongSoTien;
    this.moTa=moTa;
    this.danhSachGoiThucUong=danhSachGoiThucUong;
  }
}
