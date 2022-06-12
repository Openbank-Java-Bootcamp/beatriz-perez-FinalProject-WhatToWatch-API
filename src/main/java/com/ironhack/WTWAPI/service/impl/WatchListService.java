package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.repository.WatchListRepository;
import com.ironhack.WTWAPI.service.interfaces.WatchListServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WatchListService implements WatchListServiceInterface {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private UserRepository userRepository;


    public WatchList saveList(NewListDTO newListDTO) {
        // Handle possible errors:
        Optional<User> owner = userRepository.findById(newListDTO.getOwnerId());
        if(owner.isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified ID"); }
        // Save new item:
        log.info("Saving a new WatchList {} in the DB", newListDTO.getName());
        WatchList newList = new WatchList(newListDTO.getName(), newListDTO.getDescription(), owner.get());
        WatchList dbWatchList = watchListRepository.save(newList);
        return dbWatchList;
    }

    public List<WatchList> getLists() {
        // Handle possible errors:
        if(watchListRepository.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all WatchLists");
        return watchListRepository.findAll();

    }

    public WatchList getListById(Long listId) {
        // Handle possible errors:
        if(watchListRepository.findById(listId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No list found with the specified ID"); }
        // Return results
        return watchListRepository.findById(listId).get();
    }
}
