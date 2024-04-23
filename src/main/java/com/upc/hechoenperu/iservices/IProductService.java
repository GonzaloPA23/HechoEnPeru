package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.entities.Product;

import java.util.List;

public interface IProductService {
    Product save(Product product);
    List<Product> list();
    Product searchId(Long id) throws Exception;
    Product update(Product product) throws Exception;
    void delete(Long id) throws Exception;
}
