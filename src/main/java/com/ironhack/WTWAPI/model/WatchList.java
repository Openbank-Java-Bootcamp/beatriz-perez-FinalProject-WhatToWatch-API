package com.ironhack.WTWAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watch_list")
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "WatchLists must have a name")
    private String name;
    @NotEmpty(message = "WatchLists must have a description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "WatchLists must have an owner")
    private User owner;

    @ManyToMany
    private Collection<User> participants;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "watch_items")
    private Collection<WatchItem> watchItems;


    public WatchList(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
