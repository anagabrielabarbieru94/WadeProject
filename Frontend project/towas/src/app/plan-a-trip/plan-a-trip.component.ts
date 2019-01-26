import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-plan-a-trip',
  templateUrl: './plan-a-trip.component.html',
  styleUrls: ['./plan-a-trip.component.css']
})
export class PlanATripComponent implements OnInit {
  src_url: String;

  constructor() {
    this.src_url = "./assets/images/paris.png";
   }

  ngOnInit() {
  }

}
