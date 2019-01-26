import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  src_url: String;

  constructor() { }

  ngOnInit() {
    this.src_url = "./assets/images/tourists.png"
  }

}
