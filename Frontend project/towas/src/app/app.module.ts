import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule } from 'angularfire2/database';
import { AngularFireAuthModule } from 'angularfire2/auth';

import { RouterModule } from '@angular/router';

import { environment } from 'src/environments/environment';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { HelpComponent } from './help/help.component';
import { MyTripsComponent } from './my-trips/my-trips.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PlanATripComponent } from './plan-a-trip/plan-a-trip.component';
import { GetAnAdviceComponent } from './get-an-advice/get-an-advice.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    BsNavbarComponent,
    HomeComponent,
    AboutComponent,
    HelpComponent,
    MyTripsComponent,
    PlanATripComponent,
    GetAnAdviceComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'about', component: AboutComponent},
      {path: 'help', component: HelpComponent},
      {path: 'my-trips', component: MyTripsComponent},
      {path: 'plan-a-trip', component: PlanATripComponent},
      {path: 'get-an-advice', component: GetAnAdviceComponent},
      {path: 'sign-up', component: SignUpComponent}
    ]),
    NgbModule.forRoot(),

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
