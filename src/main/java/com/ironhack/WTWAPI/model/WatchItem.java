package com.ironhack.WTWAPI.model;

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
    private String imdbId;

    private String type;
    private String title;
    @Lob
    private String synopsis;
    @Column(name = "image_url")
    private String imageUrl;
    private String rating;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    public WatchItem(String imdbId, String type, String title, String synopsis, String imageUrl, String rating) {
        this.imdbId = imdbId;
        this.type = type;
        this.title = title;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }
}
