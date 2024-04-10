package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "roles_id", nullable = false)
    private Role roles;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @OneToMany(mappedBy = "users")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<Order> orders = new LinkedHashSet<>();

}