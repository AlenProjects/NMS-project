import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Show } from '../../show/show';
import { Theatre } from '../../theatre/theatre';
import { ScreService } from '../scre.service';
import { Scren } from '../screen';
@Component({
  selector: 'app-detail-view-screen',
  templateUrl: './detail-view-screen.component.html',
  styleUrls: ['./detail-view-screen.component.css'],
})

export class DetailViewScreenComponent implements OnInit {
  // @Input() theatreId: number;
  scr!: Scren;
  screenId!: number;
  theatre!: Theatre;
  show!: any[];
  constructor(
    private actRouter: ActivatedRoute,
    private router: Router,
    private scService: ScreService
  ) {}

  ngOnInit(): void {
    this.screenId = this.actRouter.snapshot.params['screenId'];
    this.scService.getTheatre(this.screenId).subscribe((data: any) => {
      this.theatre = data;
    });
    this.scService.getScreenById(this.screenId).subscribe((data: any) => {
      this.scr = data;
      this.show = data.show;
    });
  }
}
