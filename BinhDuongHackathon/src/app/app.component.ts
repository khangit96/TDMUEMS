
import { error } from 'util';
import { Observable } from 'rxjs/Observable';

import { Component, OnInit } from '@angular/core';
import { DataService } from 'app/shared/data.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  message:string;
  constructor(private data: DataService) { }
  ngOnInit() {
  //  this.data.currentMessage.subscribe(message => this.message = message)
  }

}
