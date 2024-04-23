package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TouristSiteDTO {
    private Long id;
    private Region region;
    private String description;
    private String image;
    private String name;
}
