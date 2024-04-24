package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Category.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    @ManyToOne(targetEntity = LocalCraftsman.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "local_craftsmen_id", nullable = false)
    private LocalCraftsman localCraftsman;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "details", nullable = false, length = Integer.MAX_VALUE)
    private String details;

    @Column(name = "history", nullable = false, length = Integer.MAX_VALUE)
    private String history;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image", nullable = false, length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "enabled")
    private Boolean enabled;
}

