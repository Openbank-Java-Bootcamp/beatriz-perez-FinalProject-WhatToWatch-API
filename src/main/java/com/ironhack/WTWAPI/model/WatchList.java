package com.ironhack.WTWAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watch_list")
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @NotEmpty(message = "WatchLists must have a name")
    private String name;
    @NotEmpty(message = "WatchLists must have a description")
    private String description;

    @ManyToOne
    @NotNull(message = "WatchLists must have an owner")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants = new HashSet<User>(){};

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "watch_items")
    private Set<WatchItem> watchItems = new HashSet<WatchItem>(){};
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> followers = new HashSet<User>(){};


    public WatchList(String name, String description, User owner) {
        this.creationDate = LocalDate.now(); // Current date
        this.name = name;
        this.description = description;
        this.owner = owner;
    }
}
