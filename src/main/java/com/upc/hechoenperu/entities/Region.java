package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "history", nullable = false, length = 200)
    private String history;

    @Column(name = "sites_introduction", nullable = false, length = 200)
    private String sitesIntroduction;

    @Column(name = "craftsmen_introduction", nullable = false, length = 200)
    private String craftsmenIntroduction;

    @OneToMany(mappedBy = "regions")
    private Set<LocalCraftsman> localCraftsmen = new LinkedHashSet<>();

    @OneToMany(mappedBy = "regions")
    private Set<TouristSite> touristSites = new LinkedHashSet<>();

}