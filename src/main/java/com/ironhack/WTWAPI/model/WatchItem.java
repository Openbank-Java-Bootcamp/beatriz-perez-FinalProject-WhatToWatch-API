package com.ironhack.WTWAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    public WatchItem(String imdbId, String type, String title, String synopsis, String image, String rating) {
        this.creationDate = LocalDate.now(); // Current date
        this.imdbId = imdbId;
        this.type = type;
        this.title = title;
        this.synopsis = synopsis;
        this.image = image;
        this.rating = rating;
    }
}
