package com.cinema.backend;

import com.cinema.backend.entities.Film;
import com.cinema.backend.services.ICinemaInitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Autowired
	private ICinemaInitService initService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class);
		initService.initVilles();
		initService.initCinemas();
		initService.initSalles();
		initService.initPlaces();
		initService.initSeances();
		initService.initCategories();
		initService.initFilms();
		initService.initProjections();
		initService.initTickets();

	}

}
