package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.RegionDTO;
import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.iservices.IRegionService;
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
public class RegionController {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/region") // http://localhost:8080/api/region
    public ResponseEntity<RegionDTO> insert(@ModelAttribute("regionDTO") RegionDTO regionDTO,
                                            @RequestParam("file")MultipartFile image) throws Exception{
        Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            region.setImage(uniqueFilename);
        }
        region = regionService.insert(region);
        regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.CREATED);
    }

    // Method Read Region
    @GetMapping("/regions") // http://localhost:8080/api/regions
    public ResponseEntity<List<RegionDTO>> list(){
        List<Region> regions = regionService.list();
        List<RegionDTO> regionDTOs = regions.stream().map(region -> dtoConverter.convertToDto(region, RegionDTO.class)).toList();
        return new ResponseEntity<>(regionDTOs, HttpStatus.OK);
    }
    //Method Update Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/region/{id}") // http://localhost:8080/api/region/1
    public ResponseEntity<RegionDTO> update(@PathVariable Long id, @ModelAttribute RegionDTO regionDTO,
                                            @RequestParam("file")MultipartFile image) throws Exception {
        Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
        region.setId(id);
        if (!image.isEmpty()) {
            String uniqueFilename = uploadFileService.copy(image);
            region.setImage(uniqueFilename);
        }
        region = regionService.update(region);
        regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }
    //Method Delete Region
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/region/{id}") // http://localhost:8080/api/region/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
        uploadFileService.delete(regionService.searchId(id).getImage());
        regionService.delete(id);
        return new ResponseEntity<>("Region deleted", HttpStatus.OK);
    }

    // Method Get Region by Id
    @GetMapping("/regionDetail/{id}") // http://localhost:8080/api/region/1
    public ResponseEntity<RegionDTO> searchId(@PathVariable Long id) throws Exception {
        Region region = regionService.searchId(id);
        RegionDTO regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }

    // Method Get para obtener la imagen
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
    @GetMapping("/regionSearch/{name}") // http://localhost:8080/api/regionName/Lima
    public ResponseEntity<RegionDTO> searchName(@PathVariable String name) throws Exception {
        Region region = regionService.searchName(name);
        RegionDTO regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }
}
