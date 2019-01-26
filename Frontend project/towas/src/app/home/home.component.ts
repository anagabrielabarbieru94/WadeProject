import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  src_url: String;

  constructor() { 
    this.src_url = './assets/images/frontpage.JPG'
  }

  ngOnInit() {
  }

}
