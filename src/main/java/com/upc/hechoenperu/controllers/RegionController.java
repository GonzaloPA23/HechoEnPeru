package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.RegionDTO;
import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.iservices.IRegionService;
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

@Tag(name = "Region")
@RestController
@RequestMapping("/api")
public class RegionController {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new region")
    @PostMapping("/region")
    public ResponseEntity<?> insert(@ModelAttribute("regionDTO") RegionDTO regionDTO,
                                            @RequestParam("file")MultipartFile image) throws Exception{
        try{
            Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                region.setImage(uniqueFilename);
            }
            region = regionService.insert(region);
            regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
            return new ResponseEntity<>(regionDTO, HttpStatus.CREATED);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Read Region
    @Operation(summary = "List all regions")
    @GetMapping("/regions")
    public ResponseEntity<?> list(){
        try{
            List<Region> regions = regionService.list();
            List<RegionDTO> regionDTOs = regions.stream().map(region -> dtoConverter.convertToDto(region, RegionDTO.class)).toList();
            return new ResponseEntity<>(regionDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Update Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a region by id")
    @PutMapping("/region/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute RegionDTO regionDTO,
                                            @RequestParam("file")MultipartFile image) throws Exception {
        try{
            Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
            region.setId(id);
            if (!image.isEmpty()) {
                String uniqueFilename = uploadFileService.copy(image);
                region.setImage(uniqueFilename);
            }
            region = regionService.update(region);
            regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
            return new ResponseEntity<>(regionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Method Delete Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a region by id")
    @DeleteMapping("/region/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
        try {
            uploadFileService.delete(regionService.searchId(id).getImage());
            regionService.delete(id);
            return new ResponseEntity<>("Region deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Method Get Region by Id
    @Operation(summary = "List a region by id")
    @GetMapping("/regionDetail/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) throws Exception {
        try{
            Region region = regionService.searchId(id);
            RegionDTO regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
            return new ResponseEntity<>(regionDTO, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method Get para obtener la imagen
    @Operation(summary = "Load image by filename")
    @GetMapping("/region/{filename}")
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

    // Method Get Region by Name
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Search a region by name")
    @GetMapping("/regionSearch/{name}")
    public ResponseEntity<?> searchName(@PathVariable String name) throws Exception {
        try{
            Region region = regionService.searchName(name);
            RegionDTO regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
            return new ResponseEntity<>(regionDTO, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
