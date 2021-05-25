package com.cinema.backend.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import com.cinema.backend.dao.CategorieRepository;
import com.cinema.backend.dao.CinemaRepository;
import com.cinema.backend.dao.FilmRepository;
import com.cinema.backend.dao.PlaceRepository;
import com.cinema.backend.dao.ProjectionRepository;
import com.cinema.backend.dao.SalleRepository;
import com.cinema.backend.dao.SeanceRepository;
import com.cinema.backend.dao.TicketRepository;
import com.cinema.backend.dao.VilleRepository;
import com.cinema.backend.entities.Categorie;
import com.cinema.backend.entities.Cinema;
import com.cinema.backend.entities.Film;
import com.cinema.backend.entities.Place;
import com.cinema.backend.entities.Projection;
import com.cinema.backend.entities.Salle;
import com.cinema.backend.entities.Seance;
import com.cinema.backend.entities.Ticket;
import com.cinema.backend.entities.Ville;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger").forEach(nameVille -> {
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
            // villeRepository.save(new Ville(null,v));
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v -> {
            Stream.of("MegaRama", "Imax", "Founoun", "Chahrazad", "Daouliz").forEach(name -> {
                Cinema cinema = new Cinema();
                cinema.setName(name);
                cinema.setNombreSalles((int) (3 + (int) (Math.random() * 7)));
                cinema.setVille(v);
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlace((20 + (int) (Math.random() * 10)));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setNumero((i + 1));
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histoire", "Actions", "Fiction", "Drama").forEach(cat -> {
            Categorie categorie = new Categorie();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[] { 1, 1.5, 2, 2.5, 3 };
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("John wick", "The Equiliazer", "Game of Thrones", "Forrest Gump", "Jumanji", "Hulk", "Spiderman")
                .forEach(mov -> {
                    Film film = new Film();
                    film.setTitre(mov);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(mov.replaceAll(" ", ""));
                    film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                    filmRepository.save(film);
                });
    }

    @Override
    public void initProjections() {
        List<Film> films = filmRepository.findAll();
        double[] prices = new double[] { 30, 50, 70, 90, 100 };
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                    seanceRepository.findAll().forEach(seance -> {
                        Projection projection = new Projection();
                        projection.setDateProjection(new Date());
                        projection.setFilm(film);
                        projection.setPrix(prices[new Random().nextInt(prices.length)]);
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }

}
