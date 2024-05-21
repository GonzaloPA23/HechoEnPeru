package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalCraftsmenByOffsetLimitResponseDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private String experience;
    private Long regionId;
    private String regionName;
    private boolean enabled;
}
