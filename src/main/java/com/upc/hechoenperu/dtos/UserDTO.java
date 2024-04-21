package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private Instant dateCreated;
    private String email;
    private String password;
    private Boolean enabled;
    private Set<Role> roles = new HashSet<>();
}
