package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.DTO.IdOnlyDTO;
import com.ironhack.WTWAPI.model.Genre;
import com.ironhack.WTWAPI.model.WatchItem;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.repository.GenreRepository;
import com.ironhack.WTWAPI.repository.WatchItemRepository;
import com.ironhack.WTWAPI.repository.WatchListRepository;
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
    private WatchListRepository watchListRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreServiceInterface genreService;

    public WatchItem saveItem(WatchItem item) {
        // Handle possible errors:
        if(watchItemRepository.findByImdbId(item.getImdbId()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new item:
        log.info("Saving a new WatchItem {} in the DB", item.getTitle());
        WatchItem newItem = new WatchItem(item.getImdbId(), item.getType(), item.getTitle(), item.getSynopsis(), item.getImage(), item.getRating(), item.getYear(), item.getBanner(), item.getCompanies(), item.getDirectors(), item.getActors(), item.getSimilars(), item.getTrailer());
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

    public List<WatchItem> getItemsByType(String type) {
        // Handle possible errors:
        if(watchItemRepository.findAllByType(type).size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all WatchItems with type {}", type);
        return watchItemRepository.findAllByType(type);
    }


    public WatchItem getItemById(Long itemId) {
        // Handle possible errors:
        if(watchItemRepository.findById(itemId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No movie or series found with the specified ID"); }
        // Return results
        return watchItemRepository.findById(itemId).get();
    }
    public WatchItem getItemByImdbId(String itemImdbId) {
        // Handle possible errors:
        if(watchItemRepository.findByImdbId(itemImdbId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No movie or series found with the specified ID"); }
        // Return results
        return watchItemRepository.findByImdbId(itemImdbId).get();
    }

    public WatchItem saveItemAndAddToList(WatchItem item, Long listId) {
        WatchItem dbItem = this.saveItem( item);
        this.addItemToList( new IdOnlyDTO(item.getId()), listId);
        return dbItem;
    }

    public void addItemToList(IdOnlyDTO itemDTO, Long listId) {
        Optional<WatchItem> item = watchItemRepository.findById(itemDTO.getId());
        Optional<WatchList> list = watchListRepository.findById(listId);
        // Handle possible errors:
        if(item.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Item not found" ); }
        if(list.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "List not found" ); }
        if(list.get().getWatchItems().contains(item.get())) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Oops, that list already contains this title!" ); }
        // Modify watchList's set of items:
        list.get().getWatchItems().add(item.get());
        // Save modified watchList
        watchListRepository.save(list.get());
    }
}
