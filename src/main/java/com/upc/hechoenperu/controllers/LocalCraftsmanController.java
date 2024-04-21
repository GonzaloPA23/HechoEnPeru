package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.LocalCraftsmanDTO;
import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.iservices.ILocalCrastmanService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api") // http://localhost:8080/api
public class LocalCraftsmanController {
    @Autowired
    private ILocalCrastmanService localCraftsmanService;
    @Autowired
    private DTOConverter dtoConverter;

    // Method Create Local Craftsman
    @PostMapping("/localCraftsman") // http://localhost:8080/api/localCraftsman
    public ResponseEntity<LocalCraftsmanDTO> insert(@RequestBody LocalCraftsmanDTO localCraftsmanDTO){
        LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
        localCraftsman = localCraftsmanService.insert(localCraftsman);
        localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
        return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
    }
    // Method Read Local Craftsman
    @GetMapping("/localCraftsmen") // http://localhost:8080/api/localCraftsmen
    public ResponseEntity<List<LocalCraftsmanDTO>> list(){
        List<LocalCraftsman> localCraftsmen = localCraftsmanService.list();
        List<LocalCraftsmanDTO> localCraftsmanDTOs = localCraftsmen.stream().map(localCraftsman -> dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class)).toList();
        return new ResponseEntity<>(localCraftsmanDTOs, HttpStatus.OK);
    }
    //Method Update Local Craftsman
    @PutMapping("/localCraftsman") // http://localhost:8080/api/localCraftsman
    public ResponseEntity<LocalCraftsmanDTO> update(@RequestBody LocalCraftsmanDTO localCraftsmanDTO) throws Exception {
        LocalCraftsman localCraftsman = dtoConverter.convertToEntity(localCraftsmanDTO, LocalCraftsman.class);
        localCraftsman = localCraftsmanService.update(localCraftsman);
        localCraftsmanDTO = dtoConverter.convertToDto(localCraftsman, LocalCraftsmanDTO.class);
        return new ResponseEntity<>(localCraftsmanDTO, HttpStatus.OK);
    }
    //Method Delete Local Craftsman
    @DeleteMapping("/localCraftsman/{id}") // http://localhost:8080/api/localCraftsman/1
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        localCraftsmanService.delete(id);
        return new ResponseEntity<>("Local craftsman deleted", HttpStatus.OK);
    }
}
