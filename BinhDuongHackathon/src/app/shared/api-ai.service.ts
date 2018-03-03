import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { ApiAiClient } from "api-ai-javascript";
import { Observable } from "rxjs/Observable";
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { Http } from "@angular/http";

@Injectable()
export class ApiAiService {
  token: string =environment.dialogflow.angularBot;;
  constructor(private http: Http) {}

  send(question: string) {
    return new ApiAiClient({ accessToken: this.token })
      .textRequest(question)
      .then(response => {
        return response;
      })
      .catch(error => {

      });
  }
}
