import { environment } from "../environments/environment";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { AppComponent } from "./app.component";
import { AngularFireModule } from "angularfire2";
import { AngularFireDatabaseModule } from "angularfire2/database";
import { RouterModule, Routes } from "@angular/router";
import { HeaderComponent } from "./header/header.component";
import { FooterComponent } from "./footer/footer.component";
import { MainSidebarComponent } from "./main-sidebar/main-sidebar.component";

import { HomNayComponent } from "./hom-nay/hom-nay.component";
import { DataService } from "app/shared/data.service";
import { LoaderComponent } from "./loader/loader.component";
import { DangNhapComponent } from "./dang-nhap/dang-nhap.component";
import { QuanTriComponent } from "./quan-tri/quan-tri.component";
import { AgmCoreModule } from "@agm/core";
import { DanhSachSuKienPhanAnhComponent } from './danh-sach-su-kien-phan-anh/danh-sach-su-kien-phan-anh.component';
import { LoaiPhanAnhComponent } from './loai-phan-anh/loai-phan-anh.component';
import { ChiTietSuKienPhanAnhComponent } from './chi-tiet-su-kien-phan-anh/chi-tiet-su-kien-phan-anh.component';
import { ApiAiService } from "app/shared/api-ai.service";
import { DemoComponent } from "app/demo/demo.component";
import { TienIchComponent } from './tien-ich/tien-ich.component';

const appRoutes: Routes = [
  { path: "dang-nhap", component: DangNhapComponent },
  { path: "demo", component: DemoComponent },
  {
    path: "quan-tri",
    component: QuanTriComponent,
    children: [
      { path: 'danh-sach-su-kien-phan-anh', component: DanhSachSuKienPhanAnhComponent, outlet: 'quan-tri' },
      { path: 'chi-tiet-su-kien-phan-anh', component: ChiTietSuKienPhanAnhComponent, outlet: 'quan-tri' },
      { path: 'loai-phan-anh/:ten', component: LoaiPhanAnhComponent, outlet: 'quan-tri' },
      { path: 'tien-ich', component: TienIchComponent, outlet: 'quan-tri' },
    ]
  },
  { path: "", redirectTo: "dang-nhap", pathMatch: "full" }
];
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainSidebarComponent,
    HomNayComponent,
    LoaderComponent,
    DangNhapComponent,
    QuanTriComponent,
    DanhSachSuKienPhanAnhComponent,
    LoaiPhanAnhComponent,
    ChiTietSuKienPhanAnhComponent,
    DemoComponent,
    TienIchComponent
],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    RouterModule.forRoot(appRoutes),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyA6_3SEYR-ppbySMmdS72DnM8V3g7l8-zs'
    })
  ],
  providers: [DataService,ApiAiService],
  bootstrap: [AppComponent]
})
export class AppModule {}
