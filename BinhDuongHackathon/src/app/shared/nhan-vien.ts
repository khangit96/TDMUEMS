export class NhanVien {
  public coQuan: string;
  public diaChi:string;
  public kinhDo: number;
  public ten: string;
  public viDo: number;
  public tinhTrang:string;
  constructor(ten: string, coQuan: string, viDo: number, kinhDo: number,diaChi:string) {
    this.ten = ten;
    this.coQuan = coQuan;
    this.viDo = viDo;
    this.kinhDo = kinhDo;
    this.diaChi=diaChi;
  }
}
