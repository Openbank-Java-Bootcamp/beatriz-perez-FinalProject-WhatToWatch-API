package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.DTO.ListParticipantDTO;
import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.service.interfaces.WatchListServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /*
    @PatchMapping("/lists/{id}/add-participant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateListParticipants(@PathVariable(name = "id") Long listId, @RequestBody ListParticipantDTO listParticipantDTO) {
        watchListService.;
    }

     */


}