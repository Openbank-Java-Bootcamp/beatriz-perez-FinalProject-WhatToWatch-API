package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchItem;
import com.ironhack.WTWAPI.service.interfaces.WatchItemServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class WatchItemController {

    @Autowired
    private WatchItemServiceInterface watchItemService;

    @PostMapping("/items/new")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchItem saveWatchItem(@RequestBody @Valid WatchItem item) {
        return watchItemService.saveItem(item);
    }

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchItem> getItems() {
        return watchItemService.getItems();
    }

    @GetMapping("/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WatchItem getWatchItemById(@PathVariable(name = "id") Long itemId) {
        return watchItemService.getItemById(itemId);
    }

}
