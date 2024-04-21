package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.TouristSiteDTO;
import com.upc.hechoenperu.entities.TouristSite;
import com.upc.hechoenperu.iservices.ITouristSiteService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api") // http://localhost:8080/api
public class TouristSiteController {
    @Autowired
    private ITouristSiteService touristSiteService;
    @Autowired
    private DTOConverter dtoConverter;
    // Method Create Tourist Site
    @PostMapping("/touristSite") // http://localhost:8080/api/touristSite
    public ResponseEntity<TouristSiteDTO> insert(@RequestBody TouristSiteDTO touristSiteDTO){
        TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
        touristSite = touristSiteService.insert(touristSite);
        touristSiteDTO = dtoConverter.convertToDto(touristSite, TouristSiteDTO.class);
        return new ResponseEntity<>(touristSiteDTO, HttpStatus.OK);
    }
    // Method Read Tourist Site
    @GetMapping("/touristSites") // http://localhost:8080/api/touristSites
    public ResponseEntity<List<TouristSiteDTO>> list(){
        List<TouristSite> touristSites = touristSiteService.list();
        List<TouristSiteDTO> touristSiteDTOs = touristSites.stream().map(touristSite -> dtoConverter.convertToDto(touristSite, TouristSiteDTO.class)).toList();
        return new ResponseEntity<>(touristSiteDTOs, HttpStatus.OK);
    }
    //Method Update Tourist Site
    @PutMapping("/touristSite") // http://localhost:8080/api/touristSite
    public ResponseEntity<TouristSiteDTO> update(@RequestBody TouristSiteDTO touristSiteDTO) throws Exception {
        TouristSite touristSite = dtoConverter.convertToEntity(touristSiteDTO, TouristSite.class);
        touristSite = touristSiteService.update(touristSite);
        touristSiteDTO = dtoConverter.convertToDto(touristSite, TouristSiteDTO.class);
        return new ResponseEntity<>(touristSiteDTO, HttpStatus.OK);
    }
    //Method Delete Tourist Site
    @DeleteMapping("/touristSite/{id}") // http://localhost:8080/api/touristSite/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        touristSiteService.delete(id);
        return new ResponseEntity<>("Tourist site deleted", HttpStatus.OK);
    }

}
