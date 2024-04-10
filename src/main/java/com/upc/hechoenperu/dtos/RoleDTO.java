package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Role;
import com.upc.hechoenperu.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private Role.NameRole nameRole;
    private Set<User> users = new LinkedHashSet<>();
}
