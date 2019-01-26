import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {
  src_url: String;

  constructor() {
    this.src_url = "./assets/images/vacation2.JPG"
  }

  ngOnInit() {
  }

}
