export class GoiThucUong {
  public key: string;
  public soLuong: number;
  public soTien: number;
  public gia:number;
  public hinhAnh:string;
  public ten:string;

  constructor(soLuong:number,soTien:number,gia:number,hinhAnh:string,ten:string){
    this.soLuong=soLuong;
    this.soTien=soTien;
    this.gia=gia;
    this.hinhAnh=hinhAnh;
    this.ten=ten;
  };

}
