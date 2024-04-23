package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.entities.Category;

import java.util.List;

public interface ICategoryService {
    Category insert(Category category);
    List<Category> list();
    Category searchId(Long id) throws Exception;
    Category update(Category category) throws Exception;
    void delete(Long id) throws Exception;
}
