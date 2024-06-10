package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.TouristSiteDTO;
import com.upc.hechoenperu.entities.TouristSite;
import com.upc.hechoenperu.iservices.ITouristSiteService;
import com.upc.hechoenperu.iservices.IUploadFileService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@Tag(name = "Tourist Site")
@RestController
@RequestMapping("/api")
public class TouristSiteController {
    @Autowired
    private ITouristSiteService touristSiteService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Tourist Site
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new tourist site")
    @PostMapping(value = {"/touristSite"},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insert(@RequestPart("touristSiteDTO") TouristSiteDTO touristSiteDTO,
                                                 @RequestPart("file") MultipartFile image) throws Exception{
        try {
            TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
            touristSite = touristSiteService.insert(touristSite);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                touristSite.setImage(uniqueFilename);
                touristSite = touristSiteService.update(touristSite);
            }
            touristSiteDTO = dtoConverter.convertToDto(touristSite, TouristSiteDTO.class);
            return new ResponseEntity<>(touristSiteDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Read Tourist Site
    @Operation(summary = "List all tourist sites")
    @GetMapping("/touristSites")
    public ResponseEntity<?> list(){
        try {
            List<TouristSite> touristSites = touristSiteService.list();
            List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
            return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Update Tourist Site
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a tourist site by id")
    @PutMapping(value = {"/touristSite/{id}"},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestPart TouristSiteDTO touristSiteDTO,
                                                 @RequestPart("file") MultipartFile image) throws Exception {
        try {
            TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
            TouristSite updateTouristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
            updateTouristSite.setId(touristSite.getId());

            if (!image.isEmpty() && !touristSite.getImage().equals(image.getOriginalFilename())) {
                uploadFileService.delete(touristSite.getImage());
                String uniqueFilename = uploadFileService.copy(image);
                updateTouristSite.setImage(uniqueFilename);
            }else {
                updateTouristSite.setImage(touristSite.getImage());
            }
            TouristSite validatedTouristSite = touristSiteService.update(updateTouristSite);
            touristSiteDTO = dtoConverter.convertToDto(validatedTouristSite, TouristSiteDTO.class);
            return new ResponseEntity<>(touristSiteDTO, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Delete Tourist Site
    @Operation(summary = "Delete a tourist site by id")
    @DeleteMapping("/touristSite/{id}") // http://localhost:8080/api/touristSite/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        try{
            //uploadFileService.delete(touristSiteService.searchId(id).getImage());
            touristSiteService.delete(id);
            return new ResponseEntity<>("Tourist site deleted", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Get para obtener la imagen
    @Operation(summary = "Load image by filename")
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

    @Operation(summary = "List all tourist sites by page")
    @GetMapping("/touristSitesByPage")
    public ResponseEntity<?> listTouristSitesByPage(@RequestParam int offset, @RequestParam int limit){
        try {
            List<TouristSite> touristSites = touristSiteService.listTouristSitesByPage(offset, limit);
            List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
            return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List all tourist sites by region")
    @GetMapping("/touristSitesByRegion/{id}")
    public ResponseEntity<?> listTouristSitesByRegion(@PathVariable Long id){
        try {
            List<TouristSite> touristSites = touristSiteService.listTouristSitesByRegion(id);
            List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
            return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List all tourist sites by region id")
    @GetMapping("/touristSitesByRegionId")
    public ResponseEntity<?> listTouristSitesByRegionId(@RequestParam Long regionId, @RequestParam int offset, @RequestParam int limit){
        try {
            List<TouristSite> touristSites = touristSiteService.listTouristSitesByRegionId(regionId, offset, limit);
            List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
            return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
