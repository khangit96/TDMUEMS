import { Component, OnInit } from '@angular/core';
import { DataService } from 'app/shared/data.service';

@Component({
  selector: 'app-quan-tri',
  templateUrl: './quan-tri.component.html',
  styleUrls: ['./quan-tri.component.css']
})
export class QuanTriComponent implements OnInit {

  //message:string;
  constructor(private data: DataService) { }
  ngOnInit() {
    //this.data.currentMessage.subscribe(message => this.message = message)
  }

}
