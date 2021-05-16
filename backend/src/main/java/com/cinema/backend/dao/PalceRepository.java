package com.cinema.backend.dao;

import com.cinema.backend.entities.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PalceRepository extends JpaRepository<Place, Long> {
}