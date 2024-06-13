package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.ProductsByAverageRatingResponseDTO;
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
    @Query("SELECT p FROM Product p WHERE p.availability = true AND p.enabled = true")
    List<Product> findAllProductsByEnabledAndAvailabilityTrue();

    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    List<Product> findAllOrderByIdAsc();

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
    @Query("SELECT new com.upc.hechoenperu.dtos.response.ProductsByAverageRatingResponseDTO(" +
            "CASE " +
            "WHEN p.averageRating >= 0 AND p.averageRating < 1 THEN '[0 a 1[' " +
            "WHEN p.averageRating >= 1 AND p.averageRating < 2 THEN '[1 a 2[' " +
            "WHEN p.averageRating >= 2 AND p.averageRating < 3 THEN '[2 a 3[' " +
            "WHEN p.averageRating >= 3 AND p.averageRating < 4 THEN '[3 a 4[' " +
            "WHEN p.averageRating >= 4 AND p.averageRating <= 5 THEN '[4 a 5]' " +
            "END, COUNT(p.id)) " +
            "FROM Product p " +
            "GROUP BY " +
            "CASE " +
            "WHEN p.averageRating >= 0 AND p.averageRating < 1 THEN '[0 a 1[' " +
            "WHEN p.averageRating >= 1 AND p.averageRating < 2 THEN '[1 a 2[' " +
            "WHEN p.averageRating >= 2 AND p.averageRating < 3 THEN '[2 a 3[' " +
            "WHEN p.averageRating >= 3 AND p.averageRating < 4 THEN '[3 a 4[' " +
            "WHEN p.averageRating >= 4 AND p.averageRating <= 5 THEN '[4 a 5]' " +
            "END")
    List<ProductsByAverageRatingResponseDTO> findProductsByAverageRating();

    //SELECT * FROM products WHERE availability = true AND enabled = true OFFSET :offset LIMIT :limit
    @Query("SELECT p FROM Product p WHERE p.availability = true AND p.enabled = true")
    List<Product> listProductsByPageModeUser(Pageable pageable);
    // SELECT products.id,products.name, c.name, r.name, lc.full_name, price,stock,average_rating, products.enabled, availability  FROM products JOIN categories c on c.id = products.categories_id JOIN local_craftsmen lc on products.local_craftsmen_id = lc.id JOIN regions r on lc.regions_id = r.id OFFSET :offset LIMIT :limit;
    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    List<Product> listProductsByPageModeAdmin(Pageable pageable);
    //SELECT p.*, SUM(od.quantity) as total_quantity
    //FROM products p
    //JOIN order_details od ON p.id = od.products_id
    //GROUP BY p.id
    //ORDER BY total_quantity DESC;
    @Query("SELECT p FROM OrderDetail od JOIN od.product p GROUP BY p.id ORDER BY SUM(od.quantity) DESC")
    List<Product> bestSellingProducts(Pageable pageable);
    @Query("SELECT p FROM OrderDetail od JOIN od.product p GROUP BY p.id ORDER BY SUM(od.quantity) DESC")
    List<Product> listBestSellingProducts();
}
