package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.entities.TouristSite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDTO {
    private Long id;
    private String name;
    private String history;
    private String image;
    private String sitesIntroduction;
    private String craftsmenIntroduction;
}
