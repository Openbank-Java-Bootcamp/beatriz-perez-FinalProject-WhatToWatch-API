package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.DTO.IdOnlyDTO;
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

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchItem> getItems() {
        return watchItemService.getItems();
    }
    @GetMapping("/items/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<WatchItem> getItemsByType(@PathVariable(name = "type") String type) {
        return watchItemService.getItemsByType(type);
    }

    @GetMapping("/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WatchItem getWatchItemById(@PathVariable(name = "id") Long itemId) {
        return watchItemService.getItemById(itemId);
    }
    @GetMapping("/items/imdb/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WatchItem getItemByImdbId(@PathVariable(name = "id") String itemImdbId) {
        return watchItemService.getItemByImdbId(itemImdbId);
    }

    @PostMapping("/items/new")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchItem saveWatchItem(@RequestBody @Valid WatchItem item) {
        return watchItemService.saveItem(item);
    }

    @PatchMapping("/items/add-to-list/{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItemToList(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "listId") String listId) {
        watchItemService.addItemToList(itemDTO, Long.parseLong(listId));
    }
    @PostMapping("/items/new/add-to-list/{listId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchItem saveItemAndAddToList(@RequestBody @Valid WatchItem item, @PathVariable(name = "listId") String listId) {
        return watchItemService.saveItemAndAddToList(item, Long.parseLong(listId));
    }

    @PatchMapping("/items/watch/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void watch(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "id") String id) {
        watchItemService.watch(itemDTO.getId(), Long.parseLong(id));
    }
    @PatchMapping("/items/like/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "id") String id) {
        watchItemService.like(itemDTO.getId(), Long.parseLong(id));
    }
    @PatchMapping("/items/unlike/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "id") String id) {
        watchItemService.unlike(itemDTO.getId(), Long.parseLong(id));
    }

}
