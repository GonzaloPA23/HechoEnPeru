package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.entities.Category;
import com.upc.hechoenperu.iservices.ICategoryService;
import com.upc.hechoenperu.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category insert(Category category) {
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }
    @Override
    public Category searchId(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found"));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category update(Category category) throws Exception {
        searchId(category.getId());
        return categoryRepository.save(category);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        categoryRepository.delete(searchId(id));
    }
}