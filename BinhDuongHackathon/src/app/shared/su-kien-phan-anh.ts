import { UrlData } from "app/shared/url-data";

export class SuKienPhanAnh {
  public kinhDo: number;
  public viDo: number;
  public thoiGian: string;
  public thoiGianNgan: string;
  public loaiPhanAnh: string;
  public moTa: string;
  public tinhTrang: string;
  public stt: number;
  public dsUrlData: UrlData[];
  public viTri: string;

  constructor(
    kinhDo: number,
    viDo: number,
    thoiGian: string,
    thoiGianNgan: string,
    loaiPhanAnh: string,
    moTa: string,
    tinhTrang: string,
    dsUrlData: UrlData[],
    viTri: string
  ) {
    this.kinhDo = kinhDo;
    this.viDo = viDo;
    this.thoiGian = thoiGian;
    this.thoiGianNgan = thoiGianNgan;
    this.loaiPhanAnh = loaiPhanAnh;
    this.moTa = moTa;
    this.tinhTrang = tinhTrang;
    this.dsUrlData = dsUrlData;
    this.viTri = viTri;
  }
}
