package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchItem;

import java.util.List;

public interface WatchItemServiceInterface {

    WatchItem saveItem(WatchItem item);
    List<WatchItem> getItems();
    WatchItem getItemById(Long itemId);

}
