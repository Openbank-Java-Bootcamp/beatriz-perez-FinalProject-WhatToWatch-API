package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.model.Genre;
import com.ironhack.WTWAPI.model.Role;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchItem;
import com.ironhack.WTWAPI.repository.GenreRepository;
import com.ironhack.WTWAPI.repository.WatchItemRepository;
import com.ironhack.WTWAPI.service.interfaces.GenreServiceInterface;
import com.ironhack.WTWAPI.service.interfaces.WatchItemServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WatchItemService implements WatchItemServiceInterface {

    @Autowired
    private WatchItemRepository watchItemRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreServiceInterface genreService;

    public WatchItem saveItem(WatchItem item) {
        // Handle possible errors:
        if(watchItemRepository.findByImdbId(item.getImdbId()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new item:
        log.info("Saving a new WatchItem {} in the DB", item.getTitle());
        WatchItem newItem = new WatchItem(item.getImdbId(), item.getType(), item.getTitle(), item.getSynopsis(), item.getImageUrl(), item.getRating());
        WatchItem dbItem = watchItemRepository.save(newItem);
        // Add genres to item:
        for (Genre genre:item.getGenres()) {
            Optional<Genre> currentGenre = genreRepository.findByName(genre.getName());
            if(currentGenre.isEmpty()) {
                log.info("Saving a new genre {} in the DB", genre.getName());
                genreService.saveGenre(new Genre(genre.getName()));
            }
            genreService.addGenreToItem(item.getImdbId(), genre.getName());
        }
        return dbItem;
    }

    public List<WatchItem> getItems() {
        // Handle possible errors:
        if(watchItemRepository.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all WatchItems");
        return watchItemRepository.findAll();
    }

    public WatchItem getItemById(Long itemId) {
        // Handle possible errors:
        if(watchItemRepository.findById(itemId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No movie or series found with the specified ID"); }
        // Return results
        return watchItemRepository.findById(itemId).get();
    }
}
