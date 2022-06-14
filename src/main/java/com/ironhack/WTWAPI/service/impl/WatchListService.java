package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.Genre;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.repository.WatchListRepository;
import com.ironhack.WTWAPI.service.interfaces.UserServiceInterface;
import com.ironhack.WTWAPI.service.interfaces.WatchListServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WatchListService implements WatchListServiceInterface {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceInterface userService;


    public WatchList saveList(NewListDTO newListDTO) {
        // Handle possible errors:
        Optional<User> owner = userRepository.findById(newListDTO.getOwnerId());
        if(owner.isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified owner ID"); }
        if(watchListRepository.findByNameAndOwner(newListDTO.getName(), owner.get()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Oops, you already have a list with that name!" ); }
        // Save new item:
        log.info("Saving a new WatchList {} in the DB", newListDTO.getName());
        WatchList newList = new WatchList(newListDTO.getName(), newListDTO.getDescription(), owner.get());
        WatchList dbWatchList = watchListRepository.save(newList);
        this.addUserToWatchListParticipants(owner.get().getId() ,dbWatchList.getId());
        return dbWatchList;
    }
    public void addUserToWatchListParticipants(Long userId, Long WatchListId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<WatchList> list = watchListRepository.findById(WatchListId);
        // Handle possible errors:
        if(user.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found" ); }
        if(list.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "List not found" ); }
        if(list.get().getParticipants().contains(user.get())) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Oops, this user already is a participant!" ); }
        // Modify watchList's set of participants:
        list.get().getParticipants().add(user.get());
        // Save modified watchList
        watchListRepository.save(list.get());
    }

    public List<WatchList> getLists() {
        // Handle possible errors:
        if(watchListRepository.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all WatchLists");
        return watchListRepository.findAll();

    }
    public List<WatchList> getListsByOwner(Long ownerId) {
        // Handle possible errors:
        if(userRepository.findById(ownerId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified owner ID"); }
        // Return results
        log.info("Fetching all WatchLists");
        List<WatchList> lists = watchListRepository.findAllByOwner(userRepository.findById(ownerId).get());
        if(lists.size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        return lists;
    }

    public WatchList getListById(Long listId) {
        // Handle possible errors:
        if(watchListRepository.findById(listId).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No list found with the specified ID"); }
        // Return results
        return watchListRepository.findById(listId).get();
    }
    public List<WatchList> getListsByName(String name) {
        log.info("Fetching all WatchLists");
        List<WatchList> lists = watchListRepository.findAllByNameContaining(name);
        // Handle possible errors:
        if(lists.size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        return lists;
    }

    public WatchList updateList(Long id, WatchList list) {
        WatchList listFromDB = watchListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        list.setId(listFromDB.getId());
        return watchListRepository.save(list);
    }

    public void deleteList(Long id) {
        WatchList listFromDB = watchListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        watchListRepository.deleteById(id);
    }

}
