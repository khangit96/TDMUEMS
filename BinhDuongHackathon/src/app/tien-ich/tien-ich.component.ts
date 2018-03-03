import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase } from "angularfire2/database";

@Component({
  selector: 'app-tien-ich',
  templateUrl: './tien-ich.component.html',
  styleUrls: ['./tien-ich.component.css']
})
export class TienIchComponent implements OnInit {

  constructor(public db: AngularFireDatabase) { }
  tieuDe="";
  noiDung="";

  ngOnInit() {
  }
  sendData(){
    const itemRef = this.db.list('TienIch');
    itemRef.push({tieuDe:this.tieuDe,noiDung:this.noiDung});
    alert('Gửi thông báo thành công');
  }

  doTextareaValueChange(ev){
    this.noiDung=ev.target.value;
  }
}
