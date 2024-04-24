package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Category;
import com.upc.hechoenperu.entities.Comment;
import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private Category category;
    private LocalCraftsman localCraftsman;
    private String name;
    private String details;
    private String history;
    private BigDecimal price;
    private String image;
    private Boolean availability;
    private Integer stock;
    private Float averageRating;
    private Boolean enabled;
}
