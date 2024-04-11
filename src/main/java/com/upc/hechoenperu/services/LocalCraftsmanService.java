package com.upc.hechoenperu.services;

import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.repositories.LocalCraftsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalCraftsmanService {
    @Autowired
    private LocalCraftsmanRepository localCraftsmanRepository;

    // Insert a new local craftsman
    public LocalCraftsman insert(LocalCraftsman localCraftsman){
        return localCraftsmanRepository.save(localCraftsman);
    }

    // List all local craftsmen
    public List<LocalCraftsman> list(){
        return localCraftsmanRepository.findAll();
    }

    // Search a local craftsman by id
    public LocalCraftsman searchId(Long id) throws Exception{
        return localCraftsmanRepository.findById(id).orElseThrow(() -> new Exception("Local craftsman not found"));
    }

    // Update a local craftsman
    public LocalCraftsman update(LocalCraftsman localCraftsman) throws Exception{
        searchId(localCraftsman.getId());
        return localCraftsmanRepository.save(localCraftsman);
    }

    // Delete a local craftsman
    public void delete(Long id) throws Exception{
        localCraftsmanRepository.delete(searchId(id));
    }
}
