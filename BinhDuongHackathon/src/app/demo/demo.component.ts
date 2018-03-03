import { Component, OnInit } from "@angular/core";
import { ApiAiService } from "app/shared/api-ai.service";
@Component({
  selector: "app-demo",
  templateUrl: "./demo.component.html",
  styleUrls: ["./demo.component.css"]
})
export class DemoComponent implements OnInit {
  lat = 10.980792;
  lng = 106.674446;
  result: any;
  constructor(private chat: ApiAiService) {}

  ngOnInit() {

    this.chat.send('Kẹt xe tại đại học thủ dầu một')
    .then((res) => {
      this.result = res;
      this.result = this.result.result.fulfillment.speech;
      console.log(this.result);
    });
  }
}
