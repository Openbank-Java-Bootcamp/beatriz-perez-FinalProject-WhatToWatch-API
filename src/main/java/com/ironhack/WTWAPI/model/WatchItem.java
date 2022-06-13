package com.ironhack.WTWAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watch_item")
public class WatchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    private String imdbId;
    private String type;
    private String title;
    @Lob
    private String synopsis;
    private String image;
    private String rating;
    private String year;
    private String banner;
    private String companies;
    private String directors;
    @Lob
    private ArrayList<Object> actors;
    @Lob
    private ArrayList<Object> similars;
    private String trailer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    public WatchItem(String imdbId, String type, String title, String synopsis, String image, String rating, String year, String banner, String companies, String directors, ArrayList<Object> actors, ArrayList<Object> similars, String trailer) {
        this.creationDate = LocalDate.now(); // Current date
        this.imdbId = imdbId;
        this.type = type;
        this.title = title;
        this.synopsis = synopsis;
        this.image = image;
        this.rating = rating;
        this.year = year;
        this.banner = banner;
        this.companies = companies;
        this.directors = directors;
        this.actors = actors;
        this.similars = similars;
        this.trailer = trailer;
    }
}
