package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.entities.Region;
import com.upc.hechoenperu.entities.TouristSite;
import com.upc.hechoenperu.iservices.ITouristSiteService;
import com.upc.hechoenperu.repositories.RegionRepository;
import com.upc.hechoenperu.repositories.TouristSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TouristSiteService implements ITouristSiteService {
    @Autowired
    private TouristSiteRepository touristSiteRepository;
    @Autowired
    private RegionRepository regionRepository;

    // Insert a new tourist site
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TouristSite insert(TouristSite touristSite){
        Validations(touristSite);
        return touristSiteRepository.save(touristSite);
    }
    // List all tourist sites
    @Override
    public List<TouristSite> list(){
        return touristSiteRepository.findAll();
    }
    // Search a tourist site by id
    @Override
    public TouristSite searchId(Long id) throws Exception{
        return touristSiteRepository.findById(id).orElseThrow(() -> new Exception("Tourist site not found"));
    }
    // Update a tourist site
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TouristSite update(TouristSite touristSite) throws Exception{
        searchId(touristSite.getId());
        return touristSiteRepository.save(touristSite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TouristSite updateWithValidation(TouristSite touristSite){
        Validations(touristSite);
        return touristSiteRepository.save(touristSite);
    }

    // Delete a tourist site
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception{
        touristSiteRepository.delete(searchId(id));
    }

    @Override
    public List<TouristSite> listTouristSitesByPage(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return touristSiteRepository.listTouristSitesByPage(pageable);
    }

    @Override
    public List<TouristSite> listTouristSitesByRegion(Long id){
        return touristSiteRepository.listTouristSitesByRegion(id);
    }

    public void Validations(TouristSite touristSite){
        Region region = regionRepository.findById(touristSite.getRegion().getId()).orElse(null);
        if(region == null){
            throw new IllegalArgumentException("Region not found");
        }
    }

    @Override
    public List<TouristSite> listTouristSitesByRegionId(Long regionId, int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return touristSiteRepository.listTouristSitesByRegionId(regionId, pageable);
    }

    @Override
    public List<TouristSite> listAllTouristSitesByRegionId(Long regionId){
        return touristSiteRepository.listAllTouristSitesByRegionId(regionId);
    }
}
