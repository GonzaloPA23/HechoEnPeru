package com.upc.hechoenperu.services;

import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // Insert a new region
    public Region insert(Region region){
        return regionRepository.save(region);
    }

    // List all regions
    public List<Region> list(){
        return regionRepository.findAll();
    }

    // Search a region by id
    public Region searchId(Long id) throws Exception{
        return regionRepository.findById(id).orElseThrow(() -> new Exception("Region not found"));
    }

    // Update a region
    public Region update(Region region) throws Exception{
        searchId(region.getId());
        return regionRepository.save(region);
    }

    // Delete a region
    public void delete(Long id) throws Exception{
        regionRepository.delete(searchId(id));
    }
}
