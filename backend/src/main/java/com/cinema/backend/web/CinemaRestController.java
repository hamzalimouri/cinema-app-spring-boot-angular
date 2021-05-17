package com.cinema.backend.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import com.cinema.backend.dao.FilmRepository;
import com.cinema.backend.entities.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home") + "/Desktop/cinema/image/" + photoName + ".jpg");
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);

    }
}
