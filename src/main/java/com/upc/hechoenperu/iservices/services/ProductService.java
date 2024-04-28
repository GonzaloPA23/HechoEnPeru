package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.dtos.response.ProductsByAverageRatingDTOResponse;
import com.upc.hechoenperu.entities.Category;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IProductService;
import com.upc.hechoenperu.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product insert(Product product) {
        // setear el averageRating de null a 0
        product.setAverageRating(0f);
        return productRepository.save(product);
    }

    @Override
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public Product searchId(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(Product product) throws Exception {
        searchId(product.getId());
        return productRepository.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        Product product = searchId(id);
        product.setEnabled(false);
        productRepository.save(product);
    }

    @Override
    public List<Product> findByPriceBetween(BigDecimal startPrice, BigDecimal endPrice) {

        if (startPrice == null || endPrice == null) {
            throw new IllegalArgumentException("startPrice and endPrice must not be null");
        }
        if (startPrice.compareTo(endPrice) > 0) {
            throw new IllegalArgumentException("startPrice must be less than or equal to endPrice");
        }

        return productRepository.findByPriceBetween(startPrice, endPrice);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findByRegionId(Long regionId) {
        return productRepository.findByRegionId(regionId);
    }

    @Override
    public List<Product> findAllByOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> findAllByOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Product> findAllByOrderByAverageRatingDesc() {
        return productRepository.findAllByOrderByAverageRatingDesc();
    }

    @Override
    public List<ProductsByAverageRatingDTOResponse> findProductsByAverageRating() {
        return productRepository.findProductsByAverageRating();
    }
}
