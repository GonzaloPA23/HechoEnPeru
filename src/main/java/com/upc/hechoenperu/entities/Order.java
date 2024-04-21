package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "orders")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

}