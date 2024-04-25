package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.dtos.response.ProductsByAverageRatingDTOResponse;
import com.upc.hechoenperu.entities.Product;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product insert(Product product);
    List<Product> list();
    Product searchId(Long id) throws Exception;
    Product update(Product product) throws Exception;
    void delete(Long id) throws Exception;
    List<Product> findByPriceBetween(BigDecimal startPrice, BigDecimal endPrice);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByRegionId(Long regionId);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByAverageRatingDesc();
    List<ProductsByAverageRatingDTOResponse> findProductsByAverageRating();
}
