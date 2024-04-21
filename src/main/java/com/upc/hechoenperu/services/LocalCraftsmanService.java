package com.upc.hechoenperu.services;

import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.iservices.ILocalCrastmanService;
import com.upc.hechoenperu.repositories.LocalCraftsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalCraftsmanService implements ILocalCrastmanService {
    @Autowired
    private LocalCraftsmanRepository localCraftsmanRepository;
    // Insert a new local craftsman
    @Override
    public LocalCraftsman insert(LocalCraftsman localCraftsman){
        return localCraftsmanRepository.save(localCraftsman);
    }
    // List all local craftsmen
    @Override
    public List<LocalCraftsman> list(){
        return localCraftsmanRepository.findAll();
    }
    // Search a local craftsman by id
    @Override
    public LocalCraftsman searchId(Long id) throws Exception{
        return localCraftsmanRepository.findById(id).orElseThrow(() -> new Exception("Local craftsman not found"));
    }
    // Update a local craftsman
    @Override
    public LocalCraftsman update(LocalCraftsman localCraftsman) throws Exception{
        searchId(localCraftsman.getId());
        return localCraftsmanRepository.save(localCraftsman);
    }
    // Delete a local craftsman
    @Override
    public void delete(Long id) throws Exception{
        localCraftsmanRepository.delete(searchId(id));
    }
}
