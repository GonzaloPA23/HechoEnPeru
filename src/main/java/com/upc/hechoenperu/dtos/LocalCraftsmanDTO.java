package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.entities.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalCraftsmanDTO {
    private Long id;
    private String fullName;
    private String description;
    private String specialty;
    private String image;
    private String experience;
    private Boolean enabled;
    private Region region;
}
