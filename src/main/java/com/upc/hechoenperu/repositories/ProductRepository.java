package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.ProductsByAverageRatingDTOResponse;
import com.upc.hechoenperu.dtos.response.ProductsByOffsetLimitResponseDTO;
import com.upc.hechoenperu.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    // List products by price range
    List<Product> findByPriceBetween(BigDecimal startPrice, BigDecimal endPrice);
    // SELECT * FROM products JOIN categories c on c.id = products.categories_id WHERE categories_id = 2;
    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    // SELECT * FROM products
    //    JOIN local_craftsmen lc on products.local_craftsmen_id = lc.id
    //    JOIN regions r on lc.regions_id = r.id
    //    WHERE regions_id = 4;
    @Query("SELECT p FROM Product p JOIN p.localCraftsman lc JOIN lc.region r WHERE r.id = :regionId")
    List<Product> findByRegionId(@Param("regionId") Long regionId);
    // List products by price in ascending order
    List<Product> findAllByOrderByPriceAsc();
    // List products by price in descending order
    List<Product> findAllByOrderByPriceDesc();
    // List products by average rating in descending order
    List<Product> findAllByOrderByAverageRatingDesc();
    //SELECT average_rating, name FROM products
    @Query("SELECT new com.upc.hechoenperu.dtos.response.ProductsByAverageRatingDTOResponse(p.averageRating, p.name) FROM Product p")
    List<ProductsByAverageRatingDTOResponse> findProductsByAverageRating();
    //SELECT * FROM products WHERE availability = true AND enabled = true OFFSET :offset LIMIT :limit
    @Query("SELECT p FROM Product p WHERE p.availability = true AND p.enabled = true")
    List<Product> listProductsByPageModeUser(Pageable pageable);
    // SELECT products.id,products.name, c.name, r.name, lc.full_name, price,stock,average_rating, products.enabled, availability  FROM products JOIN categories c on c.id = products.categories_id JOIN local_craftsmen lc on products.local_craftsmen_id = lc.id JOIN regions r on lc.regions_id = r.id OFFSET :offset LIMIT :limit;
    @Query("SELECT new com.upc.hechoenperu.dtos.response.ProductsByOffsetLimitResponseDTO(p.id, p.name, c.name, r.name, lc.fullName, p.price, p.stock, p.averageRating, p.enabled, p.availability) FROM Product p JOIN p.category c JOIN p.localCraftsman lc JOIN lc.region r")
    List<ProductsByOffsetLimitResponseDTO> listProductsByPageModeAdmin(Pageable pageable);

}
