package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.TouristSiteDTO;
import com.upc.hechoenperu.entities.TouristSite;
import com.upc.hechoenperu.iservices.ITouristSiteService;
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
public class TouristSiteController {
    @Autowired
    private ITouristSiteService touristSiteService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Tourist Site
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/touristSite") // http://localhost:8080/api/touristSite
    public ResponseEntity<TouristSiteDTO> insert(@ModelAttribute("touristSiteDTO") TouristSiteDTO touristSiteDTO,
                                                 @RequestParam("file") MultipartFile image) throws Exception{
        TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            touristSite.setImage(uniqueFilename);
        }
        touristSite = touristSiteService.insert(touristSite);
        touristSiteDTO = dtoConverter.convertToDto(touristSite, TouristSiteDTO.class);
        return new ResponseEntity<>(touristSiteDTO, HttpStatus.CREATED);
    }

    // Method Read Tourist Site
    @GetMapping("/touristSites") // http://localhost:8080/api/touristSites
    public ResponseEntity<List<TouristSiteDTO>> list(){
        List<TouristSite> touristSites = touristSiteService.list();
        List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
        return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
    }
    //Method Update Tourist Site
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/touristSite/{id}") // http://localhost:8080/api/touristSite/1
    public ResponseEntity<TouristSiteDTO> update(@PathVariable Long id, @ModelAttribute TouristSiteDTO touristSiteDTO,
                                                 @RequestParam("file") MultipartFile image) throws Exception {
        TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
        touristSite.setId(id);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            touristSite.setImage(uniqueFilename);
        }
        touristSite = touristSiteService.update(touristSite);
        touristSiteDTO = dtoConverter.convertToDto(touristSite, TouristSiteDTO.class);
        return new ResponseEntity<>(touristSiteDTO, HttpStatus.OK);
    }
    //Method Delete Tourist Site
    @DeleteMapping("/touristSite/{id}") // http://localhost:8080/api/touristSite/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        // borro la imagen
        uploadFileService.delete(touristSiteService.searchId(id).getImage());
        touristSiteService.delete(id);
        return new ResponseEntity<>("Tourist site deleted", HttpStatus.OK);
    }

    // Method Get para obtener la imagen
    @GetMapping("/touristSite/{filename}")
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
}
