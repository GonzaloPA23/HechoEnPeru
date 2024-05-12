package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.CategoryDTO;
import com.upc.hechoenperu.entities.Category;
import com.upc.hechoenperu.iservices.ICategoryService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category")
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Category

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new category")
    @PostMapping("/category")
    public ResponseEntity<?> insert(@RequestBody CategoryDTO categoryDTO){
        try{
            Category category = dtoConverter.convertToEntity(categoryDTO, Category.class);
            category = categoryService.insert(category);
            categoryDTO = dtoConverter.convertToDto(category, CategoryDTO.class);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Method Read Category
    @Operation(summary = "List all categories")
    @GetMapping("/categories")
    public ResponseEntity<?> list(){
        try{
            List<Category> categories = categoryService.list();
            List<CategoryDTO> categoryDTOs = categories.stream().map(category -> dtoConverter.convertToDto(category, CategoryDTO.class)).toList();
            return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    //Method Update Category
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a category")
    @PutMapping("/category")
    public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDTO) throws Exception {
        try{
            Category category = dtoConverter.convertToEntity(categoryDTO, Category.class);
            category = categoryService.update(category);
            categoryDTO = dtoConverter.convertToDto(category, CategoryDTO.class);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
