import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-locationcomponent',
  templateUrl: './locationcomponent.component.html',
  styleUrls: ['./locationcomponent.component.css']
})
export class LocationcomponentComponent implements OnInit {
  isTracking: boolean;
  isClicked: boolean;
  @Input("currentLat") currentLat: number;
  @Input("currentLong") currentLong: number;

  constructor() { }

  ngOnInit() {
  }

  setPosition(position){
    this.currentLat = position.coords.latitude;
    this.currentLong = position.coords.longitude;
  }

  findMe() {
    this.isClicked = true;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.setPosition(position);
      });
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  }

}
