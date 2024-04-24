package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.ProductDTO;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IProductService;
import com.upc.hechoenperu.iservices.IUploadFileService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

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
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> insert(@ModelAttribute("productDTO") ProductDTO productDTO,
                                             @RequestParam("file") MultipartFile image) throws Exception {
        Product product = dtoConverter.convertToEntity(productDTO, Product.class);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            product.setImage(uniqueFilename);
        }
        product = productService.insert(product);
        productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
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
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @ModelAttribute ProductDTO productDTO,
                                             @RequestParam("file") MultipartFile image) throws Exception {
        Product product = dtoConverter.convertToEntity(productDTO, Product.class);
        product.setId(id);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            product.setImage(uniqueFilename);
        }
        product = productService.update(product);
        productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //Method Delete Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/productDelete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        uploadFileService.delete(productService.searchId(id).getImage());
        productService.delete(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    // Method Get for obtaining the image
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
    @GetMapping("/productDetails/{id}")
    public ResponseEntity<ProductDTO> searchId(@PathVariable Long id) throws Exception {
        Product product = productService.searchId(id);
        ProductDTO productDTO = dtoConverter.convertToDto(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
