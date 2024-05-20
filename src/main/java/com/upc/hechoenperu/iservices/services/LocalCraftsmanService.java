package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.entities.LocalCraftsman;
import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.iservices.ILocalCrastmanService;
import com.upc.hechoenperu.repositories.LocalCraftsmanRepository;
import com.upc.hechoenperu.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocalCraftsmanService implements ILocalCrastmanService {
    @Autowired
    private LocalCraftsmanRepository localCraftsmanRepository;
    @Autowired
    private RegionRepository regionRepository;
    // Insert a new local craftsman
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalCraftsman insert(LocalCraftsman localCraftsman){
        validations(localCraftsman);
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
    @Transactional(rollbackFor = Exception.class)
    public LocalCraftsman update(LocalCraftsman localCraftsman) throws Exception{
        searchId(localCraftsman.getId());
        return localCraftsmanRepository.save(localCraftsman);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalCraftsman updateWithValidations(LocalCraftsman localCraftsman) throws Exception{
        searchId(localCraftsman.getId());
        validations(localCraftsman);
        return localCraftsmanRepository.save(localCraftsman);
    }

    // Delete a local craftsman
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception{
        LocalCraftsman localCraftsman = searchId(id);
        localCraftsman.setEnabled(false);
        localCraftsmanRepository.save(localCraftsman);
    }

    public void validations(LocalCraftsman localCraftsman){
        // si la region no existe, se lanza una excepcion
        Region region = regionRepository.findById(localCraftsman.getRegion().getId()).orElse(null);
        if(region == null){
            throw new IllegalArgumentException("The region does not exist");
        }
    }
}
