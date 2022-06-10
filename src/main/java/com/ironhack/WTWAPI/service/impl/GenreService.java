package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.model.Genre;
import com.ironhack.WTWAPI.model.Role;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchItem;
import com.ironhack.WTWAPI.repository.GenreRepository;
import com.ironhack.WTWAPI.repository.RoleRepository;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.repository.WatchItemRepository;
import com.ironhack.WTWAPI.service.interfaces.GenreServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreService implements GenreServiceInterface {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private WatchItemRepository watchItemRepository;


    public List<Genre> getAllGenres() {
        // Handle error:
        if(genreRepository.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Show results
        log.info("Fetching all existing roles");
        return genreRepository.findAll();
    }

    public Genre saveGenre(Genre genre) {
        // Handle possible errors:
        if(genreRepository.findByName(genre.getName()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new genre:
        log.info("Saving a new genre {} to the database", genre.getName());
        return genreRepository.save(genre);
    }

    public void addGenreToItem(String imdbId, String genreName) {
        Optional<WatchItem> item = watchItemRepository.findByImdbId(imdbId);
        Optional<Genre> genre = genreRepository.findByName(genreName);
        // Handle possible errors:
        if(item.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Item not found" ); }
        if(genre.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Genre not found" ); }
        // Modify item's list of genres:
        item.get().getGenres().add(genre.get());
        // Save modified item
        watchItemRepository.save(item.get());
    }
}
