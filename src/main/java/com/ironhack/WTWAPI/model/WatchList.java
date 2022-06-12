package com.ironhack.WTWAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
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
    @NotEmpty(message = "WatchLists must have a name")
    private String name;
    @NotEmpty(message = "WatchLists must have a description")
    private String description;

    @ManyToOne
    @NotNull(message = "WatchLists must have an owner")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "watch_items")
    private Set<WatchItem> watchItems;

    public WatchList(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }
}
