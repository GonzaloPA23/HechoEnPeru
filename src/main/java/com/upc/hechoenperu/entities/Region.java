package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "history", nullable = false, length = Integer.MAX_VALUE)
    private String history;

    @Column(name = "image", nullable = false, length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "sites_introduction", nullable = false, length = Integer.MAX_VALUE)
    private String sitesIntroduction;

    @Column(name = "craftsmen_introduction", nullable = false, length = Integer.MAX_VALUE)
    private String craftsmenIntroduction;

}