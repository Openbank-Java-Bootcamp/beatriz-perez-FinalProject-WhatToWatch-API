package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.model.Genre;
import com.ironhack.WTWAPI.service.interfaces.GenreServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class GenreController {

    @Autowired
    private GenreServiceInterface genreService;

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getGenres() {
        return genreService.getAllGenres();
    }

}
