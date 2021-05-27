import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CinemaService {

  // tslint:disable-next-line:no-inferrable-types
  public host: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  // tslint:disable-next-line:typedef
  public getVilles(){
    return this.http.get(this.host + '/villes');
  }

  // tslint:disable-next-line:typedef
  public getCinemas(v: any){
    return this.http.get(v._links.cinemas.href);
  }
  // tslint:disable-next-line:typedef
  public getSalles(c: any){
    return this.http.get(c._links.salles.href);
  }
    // tslint:disable-next-line:typedef
  public getProjections(s: any){
    const url = s._links.projections.href.replace('{?projection}', '');
    return this.http.get(url + '?projection=p1');
  }
  // tslint:disable-next-line:typedef
  public getTicketsPlaces(p: any){
    const url = p._links.tickets.href.replace('{?projection}', '');
    return this.http.get(url + '?projection=pticket');
  }

  // tslint:disable-next-line:typedef
  public payTickets(data: any){
    return this.http.post(this.host + '/payerTickets', data);
  }
}
