package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.iservices.IRegionService;
import com.upc.hechoenperu.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegionService implements IRegionService {
    @Autowired
    private RegionRepository regionRepository;

    // Insert a new region
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Region insert(Region region){
        return regionRepository.save(region);
    }
    // List all regions
    @Override
    public List<Region> list(){
        return regionRepository.findAll();
    }
    // Search a region by id
    @Override
    public Region searchId(Long id) throws Exception{
        return regionRepository.findById(id).orElseThrow(() -> new Exception("Region not found"));
    }
    // Update a region
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Region update(Region region) throws Exception{
        searchId(region.getId());
        return regionRepository.save(region);
    }
    // Delete a region
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception{
        regionRepository.delete(searchId(id));
    }
    // Search a region by name
    @Override
    public Region searchName(String name) throws Exception{
        return regionRepository.findByName(name);
    }
}
