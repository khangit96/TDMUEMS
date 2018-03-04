export class CoQuan {
  public ten: string;
  public soDienThoai: string;
  public diaChi: string;
  public email: string;
  public fax: string;
  public kinhDo: number;
  public viDo: number;
  public stt?:number;
  public key:string;
  constructor(
    ten: string,
    soDienThoai: string,
    diaChi: string,
    email: string,
    fax: string,
    kinhDo: number,
    viDo: number
  ) {
    this.ten = ten;
    this.soDienThoai = soDienThoai;
    this.diaChi = diaChi;
    this.email = email;
    this.fax = fax;
    this.kinhDo = kinhDo;
    this.viDo = viDo;
  }
}
