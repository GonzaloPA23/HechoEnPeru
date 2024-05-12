package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.ProductDTO;
import com.upc.hechoenperu.dtos.response.ProductsByAverageRatingDTOResponse;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IProductService;
import com.upc.hechoenperu.iservices.IUploadFileService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

@Tag(name = "Product")
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    //Method Create Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new product")
    @PostMapping("/product")
    public ResponseEntity<?> insert(@ModelAttribute("productDTO") ProductDTO productDTO,
                                             @RequestParam("file") MultipartFile image) throws Exception {
        try {
            Product product = dtoConverter.convertToEntity(productDTO, Product.class);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                product.setImage(uniqueFilename);
            }
            product = productService.insert(product);
            productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
            return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Method Read Product
    @Operation(summary = "List all products")
    @GetMapping("/products")
    public ResponseEntity<?> list(){
        try{
            List<Product> products = productService.list();
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Method Update Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a product by id")
    @PutMapping("/product/{id}")
        public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute ProductDTO productDTO,
                                             @RequestParam("file") MultipartFile image) throws Exception {
        try{
            Product product = dtoConverter.convertToEntity(productDTO, Product.class);
            product.setId(id);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                product.setImage(uniqueFilename);
            }
            product = productService.update(product);
            productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Method Delete Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a product by id")
    @DeleteMapping("/productDelete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        try{
            uploadFileService.delete(productService.searchId(id).getImage());
            productService.delete(id);
            return new ResponseEntity<>("Product deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Method Get for obtaining the image
    @Operation(summary = "Load image by filename")
    @GetMapping("/uploadsLoadImage/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename){
        Resource resource = null;
        try{
            resource = uploadFileService.load(filename);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Method Search Product by Id
    @Operation(summary = "Search a product by id")
    @GetMapping("/productDetails/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) throws Exception {
        try {
            Product product = productService.searchId(id);
            ProductDTO productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Search Product by Price (startPrice <= price <= endPrice)
    @Operation(summary = "Search products by price")
    @GetMapping("/productsByPrice")
    public ResponseEntity<?> findByPriceBetween(@RequestParam("startPrice") BigDecimal startPrice, @RequestParam("endPrice") BigDecimal endPrice) {
       try {
           List<Product> products = productService.findByPriceBetween(startPrice, endPrice);
           List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
           return new ResponseEntity<>(productDTOs, HttpStatus.OK);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    // Method Search Product by Category
    @Operation(summary = "Search products by category")
    @GetMapping("/productsByCategory")
    public ResponseEntity<?> findByCategoryIdOrderBy(@RequestParam("categoryId") Long categoryId) {
        try{
            List<Product> products = productService.findByCategoryId(categoryId);
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Search Product by Region
    @Operation(summary = "Search products by region")
    @GetMapping("/productsByRegion")
    public ResponseEntity<?> findByRegionId(@RequestParam("regionId") Long regionId) {
        try{
            List<Product> products = productService.findByRegionId(regionId);
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method List Products by Price in Ascending Order
    @Operation(summary = "List products by price in ascending order")
    @GetMapping("/productsByPriceAsc")
    public ResponseEntity<?> findAllByOrderByPriceAsc() {
        try{
            List<Product> products = productService.findAllByOrderByPriceAsc();
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method List Products by Price in Descending Order
    @Operation(summary = "List products by price in descending order")
    @GetMapping("/productsByPriceDesc")
    public ResponseEntity<?> findAllByOrderByPriceDesc() {
        try{
            List<Product> products = productService.findAllByOrderByPriceDesc();
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method List Products by Average Rating in Descending Order
    @Operation(summary = "List products by average rating in descending order")
    @GetMapping("/productsByAverageRatingDesc")
    public ResponseEntity<?> findAllByOrderByAverageRatingDesc() {
        try{
            List<Product> products = productService.findAllByOrderByAverageRatingDesc();
            List<ProductDTO> productDTOs = products.stream().map(product -> dtoConverter.convertToDto(product, ProductDTO.class)).toList();
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method List Products by Average Rating
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List products by average rating")
    @GetMapping("/productsByAverageRating")
    public ResponseEntity<?> findProductsByAverageRating() {
          try{
              List<ProductsByAverageRatingDTOResponse> products = productService.findProductsByAverageRating();
              return new ResponseEntity<>(products, HttpStatus.OK);
          }catch (Exception e){
              return ResponseEntity.badRequest().body(e.getMessage());
          }
    }
}
