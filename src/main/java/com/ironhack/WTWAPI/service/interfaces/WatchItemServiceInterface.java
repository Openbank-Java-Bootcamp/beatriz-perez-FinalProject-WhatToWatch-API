package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.DTO.IdOnlyDTO;
import com.ironhack.WTWAPI.model.WatchItem;

import java.util.List;

public interface WatchItemServiceInterface {

    List<WatchItem> getItems();
    List<WatchItem> getItemsByType(String type);
    WatchItem getItemById(Long itemId);
    WatchItem getItemByImdbId(String itemImdbId);
    WatchItem saveItem(WatchItem item);
    WatchItem saveItemAndAddToList(WatchItem item, Long listId);
    void addItemToList(IdOnlyDTO itemDTO, Long listId);
}
