package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.WatchList;

import java.util.List;

public interface WatchListServiceInterface {

    WatchList saveList(NewListDTO newListDTO);
    List<WatchList> getLists();
    List<WatchList> getListsByOwner(Long ownerId);
    List<WatchList> getListsByName(String name);
    WatchList getListById(Long listId);
    void addUserToWatchListParticipants(Long userId, Long WatchListId);
    WatchList updateList(Long id, WatchList list);
    void deleteList(Long id);

}
