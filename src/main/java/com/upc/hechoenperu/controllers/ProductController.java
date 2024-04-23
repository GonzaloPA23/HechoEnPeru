package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.ProductDTO;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IProductService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private DTOConverter dtoConverter;

    //Method Create Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO){
        Product product = dtoConverter.convertToEntity(productDTO, Product.class);
        product = productService.save(product);
        productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //Method Read Product
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> list(){
        List<Product> products = productService.list();
        List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    //Method Update Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/product")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) throws Exception {
        Product product = dtoConverter.convertToEntity(productDTO, Product.class);
        product = productService.update(product);
        productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //Method Delete Product
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        productService.delete(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
}
