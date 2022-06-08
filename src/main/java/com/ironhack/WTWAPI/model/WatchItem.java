package com.ironhack.WTWAPI.model;

import com.ironhack.WTWAPI.enums.Status;
import com.ironhack.WTWAPI.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String title;

    private String synopsis;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer rating;

    private Type type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    private Status status;


    public WatchItem(String title, String synopsis, String imageUrl, Integer rating, Type type, List<Genre> genres, Status status) {
        this.title = title;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.type = type;
        this.genres = genres;
        this.status = status;
    }
}