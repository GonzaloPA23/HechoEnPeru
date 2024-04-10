package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name_role", nullable = false)
    private NameRole nameRole;

    @OneToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    public enum NameRole {
        ADMIN,
        CLIENTE
    }

/*
    TODO [JPA Buddy] create field to map the 'name_role' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "name_role", columnDefinition = "namerole(0, 0) not null")
    private Object nameRole;
*/
}