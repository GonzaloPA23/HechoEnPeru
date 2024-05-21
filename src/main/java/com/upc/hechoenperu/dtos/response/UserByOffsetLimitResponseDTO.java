package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserByOffsetLimitResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String role;
    private String dateCreated;
    private Boolean enabled;
}

