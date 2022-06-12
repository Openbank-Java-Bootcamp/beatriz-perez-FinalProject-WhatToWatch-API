package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.DTO.NewListDTO;
import com.ironhack.WTWAPI.model.WatchList;

import java.util.List;

public interface WatchListServiceInterface {

    WatchList saveList(NewListDTO newListDTO);
    List<WatchList> getLists();
    WatchList getListById(Long listId);

}
