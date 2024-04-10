package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Comment;
import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private Role roles;
    private String name;
    private String lastName;
    private Instant dateCreated;
    private String email;
    private String password;
    private Set<Comment> comments = new LinkedHashSet<>();
    private Set<Order> orders = new LinkedHashSet<>();
}
