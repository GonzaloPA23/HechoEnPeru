package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categories_id", nullable = false)
    private Category categories;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "local_craftsmen_id", nullable = false)
    private LocalCraftsman localCraftsmen;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "details", nullable = false, length = 200)
    private String details;

    @Column(name = "history", nullable = false, length = 200)
    private String history;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "images", columnDefinition = "text[]")
    @Convert(converter = StringListConverter.class)
    private List<String> images;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "average_rating")
    private Float averageRating;

    @OneToMany(mappedBy = "products")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "products")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

}

@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}