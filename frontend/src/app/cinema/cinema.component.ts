import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CinemaService } from '../services/cinema.service';

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.scss']
})
export class CinemaComponent implements OnInit {

  public villes: any;
  public cinemas: any;
  public salles: any;
  public currentVille: any;
  public currentCinema: any;
  public currentProjection: any;
  public currentSalles: any;
  public selectedTickets: any;
  constructor(public cinemaService: CinemaService) { }

  ngOnInit(): void {
    this.cinemaService.getVilles()
      .subscribe(data => {
        this.villes = data;
      }, err => {
        console.log(err);
      });
  }

  // tslint:disable-next-line:typedef
  onGetCinemas(v: any){
    this.currentVille = v;
    this.salles = undefined;
    this.cinemaService.getCinemas(v)
    .subscribe(data => {
        this.cinemas = data;
      }, err => {
        console.log(err);
      });
  }

  // tslint:disable-next-line:typedef
  onGetSalles(c: any){
    this.currentCinema = c;
    this.cinemaService.getSalles(c)
    .subscribe(data => {
        this.salles = data;
        this.salles._embedded.salles.forEach((salle: any) => {
          this.cinemaService.getProjections(salle)
          // tslint:disable-next-line:no-shadowed-variable
          .subscribe(data => {
            salle.projections = data;
          }, err => {
            console.log(err);
          });
        });
      }, err => {
        console.log(err);
      });
  }
  // tslint:disable-next-line:typedef
  onGetTicketsPlaces(p: any){
    this.currentProjection = p;
    this.cinemaService.getTicketsPlaces(p)
    // tslint:disable-next-line:no-shadowed-variable
        .subscribe(data => {
          this.currentProjection.tickets = data;
          this.selectedTickets = [];
        }, err => {
          console.log(err);
        });
  }
  // tslint:disable-next-line:typedef
  onSelectTicket(t: any){
    if (!t.selected){
      t.selected = true;
      this.selectedTickets.push(t);
    }else{
      t.selected = false;
      this.selectedTickets.splice(this.selectedTickets.indexOf(t));
    }
    console.log(this.selectedTickets);
  }


  // tslint:disable-next-line:typedef
  getTicketClass(t: any){
    let str = 'btn ticket ';
    if (t.reserve){
      str += 'btn-danger';
    }else if (t.selected){
      str += 'btn-warning';
    }else{
      str += 'btn-success';
    }
    return str;
  }
}
