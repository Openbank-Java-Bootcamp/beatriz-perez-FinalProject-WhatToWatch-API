package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre,Long> {
}

