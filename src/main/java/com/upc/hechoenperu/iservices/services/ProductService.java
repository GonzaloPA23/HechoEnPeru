package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IProductService;
import com.upc.hechoenperu.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product insert(Product product) {
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
    public Product update(Product product) throws Exception {
        searchId(product.getId());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) throws Exception {
        Product product = searchId(id);
        product.setEnabled(false);
        productRepository.save(product);
    }
}
