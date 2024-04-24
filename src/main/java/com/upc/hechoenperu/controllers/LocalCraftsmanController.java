package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.LocalCraftsmanDTO;
import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.iservices.ILocalCrastmanService;
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
    @PostMapping("/localCraftsman") // http://localhost:8080/api/localCraftsman
    public ResponseEntity<LocalCraftsmanDTO> insert (@ModelAttribute("localCraftsmanDTO") LocalCraftsmanDTO localCraftsmanDTO,
                                                     @RequestParam("file") MultipartFile image) throws Exception {
        LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            localCraftsman.setImage(uniqueFilename);
        }
        localCraftsman = localCraftsmanService.insert(localCraftsman);
        localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
        return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.CREATED);
    }
    // Method Read Local Craftsman
    @GetMapping("/localCraftsmen") // http://localhost:8080/api/localCraftsmen
    public ResponseEntity<List<LocalCraftsmanDTO>> list(){
        List<LocalCraftsman> localCraftsmen = localCraftsmanService.list();
        List<LocalCraftsmanDTO> localCraftsmanDTOs = localCraftsmen.stream().map(localCraftsman -> dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class)).toList();
        return new ResponseEntity<>(localCraftsmanDTOs, HttpStatus.OK);
    }
    //Method Update Local Craftsman
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/localCraftsman/{id}") // http://localhost:8080/api/localCraftsman/1
    public ResponseEntity<LocalCraftsmanDTO> update(@PathVariable Long id, @ModelAttribute LocalCraftsmanDTO localCraftsmanDTO,
                                                    @RequestParam("file") MultipartFile image) throws Exception {
        LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
        localCraftsman.setId(id);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            localCraftsman.setImage(uniqueFilename);
        }
        localCraftsman = localCraftsmanService.update(localCraftsman);
        localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
        return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
    }
    //Method Delete Local Craftsman
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/localCraftsmanDelete/{id}") // http://localhost:8080/api/localCraftsman/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        uploadFileService.delete(localCraftsmanService.searchId(id).getImage());
        localCraftsmanService.delete(id);
        return new ResponseEntity<>("Local craftsman deleted", HttpStatus.OK);
    }

    // Method Get para obtener la imagen
    @GetMapping("/localCraftsmanLoadImage/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Method Get by id
    @GetMapping("/localCraftsmanDetail/{id}")
    public ResponseEntity<LocalCraftsmanDTO> searchId(@PathVariable Long id) throws Exception {
        LocalCraftsman localCraftsman = localCraftsmanService.searchId(id);
        LocalCraftsmanDTO localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
        return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
    }
}
