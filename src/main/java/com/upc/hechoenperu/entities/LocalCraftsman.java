package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_craftsmen")
public class LocalCraftsman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Region.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "regions_id", nullable = false)
    private Region region;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "specialty", nullable = false, length = 100)
    private String specialty;

    @Column(name = "image", nullable = false, length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "experience", nullable = false, length = 100)
    private String experience;

    @Column(name = "enabled")
    private Boolean enabled;

}