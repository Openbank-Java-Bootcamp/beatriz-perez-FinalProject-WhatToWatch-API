package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.Genre;

import java.util.List;

public interface GenreServiceInterface {
    List<Genre> getAllGenres();
    Genre saveGenre(Genre genre);
    void addGenreToItem(String imdbId, String genreName);
}
