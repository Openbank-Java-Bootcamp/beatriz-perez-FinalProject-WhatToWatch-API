package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.DTO.IdOnlyDTO;
import com.ironhack.WTWAPI.DTO.ListParticipantDTO;
import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.repository.WatchListRepository;
import com.ironhack.WTWAPI.service.interfaces.WatchListServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class WatchListController {

    @Autowired
    private WatchListServiceInterface watchListService;

    @PostMapping("/lists/new")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchList saveList(@RequestBody @Valid NewListDTO newListDTO) {
        return watchListService.saveList(newListDTO);
    }

    @GetMapping("/lists")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchList> getLists() {
        return watchListService.getLists();
    }

    @GetMapping("/lists/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WatchList getWatchListById(@PathVariable(name = "id") Long listId) {
        return watchListService.getListById(listId);
    }
    @GetMapping("/lists/owner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchList> getWatchListsByOwner(@PathVariable(name = "ownerId") Long ownerId) {
        return watchListService.getListsByOwner(ownerId);
    }
    @GetMapping("/lists/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchList> getWatchListsByName(@PathVariable(name = "name") String name) {
        return watchListService.getListsByName(name);
    }

    @PatchMapping("/lists/follow/{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "listId") String listId) {
        watchListService.follow(itemDTO.getId(), Long.parseLong(listId));
    }
    @PatchMapping("/lists/unfollow/{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "listId") String listId) {
        watchListService.unfollow(itemDTO.getId(), Long.parseLong(listId));
    }
    @PatchMapping("/lists/participant/{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUserToWatchListParticipants(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "listId") String listId) {
        watchListService.addUserToWatchListParticipants(itemDTO.getId(), Long.parseLong(listId));
    }

    /*
    @PatchMapping("/lists/item/{listId}")
     */

    @PutMapping("/lists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public WatchList updateList(@PathVariable Long id,@RequestBody @Valid WatchList list){
        return watchListService.updateList(id, list);
    }

    @DeleteMapping("/lists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteList(@PathVariable Long id){
        watchListService.deleteList(id);
    }

}
