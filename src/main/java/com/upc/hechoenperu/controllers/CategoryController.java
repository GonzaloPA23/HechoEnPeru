package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.CategoryDTO;
import com.upc.hechoenperu.entities.Category;
import com.upc.hechoenperu.iservices.ICategoryService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Category
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDTO){
        Category category = dtoConverter.convertToEntity(categoryDTO, Category.class);
        category = categoryService.insert(category);
        categoryDTO = dtoConverter.convertToDto(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    // Method Read Category
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> list(){
        List<Category> categories = categoryService.list();
        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> dtoConverter.convertToDto(category, CategoryDTO.class)).toList();
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    //Method Update Category
    @PutMapping("/category")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) throws Exception {
        Category category = dtoConverter.convertToEntity(categoryDTO, Category.class);
        category = categoryService.update(category);
        categoryDTO = dtoConverter.convertToDto(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
