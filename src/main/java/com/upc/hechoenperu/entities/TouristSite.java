package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tourist_sites")
public class TouristSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Region.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "regions_id", nullable = false)
    private Region regions;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "image", nullable = false, length = 50)
    private String image;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

}