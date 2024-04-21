package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.RegionDTO;
import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.iservices.IRegionService;
import com.upc.hechoenperu.services.RegionService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api") // http://localhost:8080/api
public class RegionController {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Region
    @PostMapping("/region") // http://localhost:8080/api/region
    public ResponseEntity<RegionDTO> insert(@RequestBody RegionDTO regionDTO){
        Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
        region = regionService.insert(region);
        regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }

    // Method Read Region
    @GetMapping("/regions") // http://localhost:8080/api/regions
    public ResponseEntity<List<RegionDTO>> list(){
        List<Region> regions = regionService.list();
        List<RegionDTO> regionDTOs = regions.stream().map(region -> dtoConverter.convertToDto(region, RegionDTO.class)).toList();
        return new ResponseEntity<>(regionDTOs, HttpStatus.OK);
    }

    //Method Update Region
    @PutMapping("/region") // http://localhost:8080/api/region
    public ResponseEntity<RegionDTO> update(@RequestBody RegionDTO regionDTO) throws Exception {
        Region region = dtoConverter.convertToEntity(regionDTO, Region.class);
        region = regionService.update(region);
        regionDTO = dtoConverter.convertToDto(region, RegionDTO.class);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }

    //Method Delete Region
    @DeleteMapping("/region/{id}") // http://localhost:8080/api/region/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        regionService.delete(id);
        return new ResponseEntity<>("Region deleted", HttpStatus.OK);
    }
}
