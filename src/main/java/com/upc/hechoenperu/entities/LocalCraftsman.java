package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "local_craftsmen")
public class LocalCraftsman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "regions_id", nullable = false)
    private Region regions;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "specialty", nullable = false, length = 50)
    private String specialty;

    @Column(name = "image", nullable = false, length = 50)
    private String image;

    @Column(name = "experience", nullable = false, length = 50)
    private String experience;

    @OneToMany(mappedBy = "localCraftsmen")
    private Set<Product> products = new LinkedHashSet<>();

}