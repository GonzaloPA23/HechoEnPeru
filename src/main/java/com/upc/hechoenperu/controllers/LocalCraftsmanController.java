package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.LocalCraftsmanDTO;
import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.iservices.ILocalCrastmanService;
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

import java.net.MalformedURLException;
import java.util.List;

@Tag(name = "Local Craftsman")
@RestController
@RequestMapping("/api") // http://localhost:8080/api
public class LocalCraftsmanController {
    @Autowired
    private ILocalCrastmanService localCraftsmanService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Local Craftsman
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new local craftsman")
    @PostMapping("/localCraftsman") // http://localhost:8080/api/localCraftsman
    public ResponseEntity<?> insert (@ModelAttribute("localCraftsmanDTO") LocalCraftsmanDTO localCraftsmanDTO,
                                                     @RequestParam("file") MultipartFile image) throws Exception {

        try {
            LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                localCraftsman.setImage(uniqueFilename);
            }
            localCraftsman = localCraftsmanService.insert(localCraftsman);
            localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
            return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Method Read Local Craftsman
    @Operation(summary = "List all local craftsmen")
    @GetMapping("/localCraftsmen") // http://localhost:8080/api/localCraftsmen
    public ResponseEntity<?> list(){
        try{
            List<LocalCraftsman> localCraftsmen = localCraftsmanService.list();
            List<LocalCraftsmanDTO> localCraftsmanDTOs = localCraftsmen.stream().map(localCraftsman -> dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class)).toList();
            return new ResponseEntity<>(localCraftsmanDTOs, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Update Local Craftsman
    @Operation(summary = "Update a local craftsman by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/localCraftsman/{id}") // http://localhost:8080/api/localCraftsman/1
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute LocalCraftsmanDTO localCraftsmanDTO,
                                                    @RequestParam("file") MultipartFile image) throws Exception {
        try{
            LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
            localCraftsman.setId(id);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                localCraftsman.setImage(uniqueFilename);
            }
            localCraftsman = localCraftsmanService.update(localCraftsman);
            localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
            return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Delete Local Craftsman
    @Operation(summary = "Delete a local craftsman by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/localCraftsmanDelete/{id}") // http://localhost:8080/api/localCraftsman/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        try{
            uploadFileService.delete(localCraftsmanService.searchId(id).getImage());
            localCraftsmanService.delete(id);
            return new ResponseEntity<>("Local craftsman deleted", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Get para obtener la imagen
    @Operation(summary = "Load image by filename")
    @GetMapping("/localCraftsmanLoadImage/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert resource != null;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Method Get by id
    @Operation(summary = "Search a local craftsman by id")
    @GetMapping("/localCraftsmanDetail/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) throws Exception {
        try{
            LocalCraftsman localCraftsman = localCraftsmanService.searchId(id);
            LocalCraftsmanDTO localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
            return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
