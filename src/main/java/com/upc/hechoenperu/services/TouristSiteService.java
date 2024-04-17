package com.upc.hechoenperu.services;

import com.upc.hechoenperu.entities.TouristSite;
import com.upc.hechoenperu.repositories.TouristSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristSiteService {
    @Autowired
    private TouristSiteRepository touristSiteRepository;

    // Insert a new tourist site
    public TouristSite insert(TouristSite touristSite){
        return touristSiteRepository.save(touristSite);
    }

    // List all tourist sites
    public List<TouristSite> list(){
        return touristSiteRepository.findAll();
    }

    // Search a tourist site by id
    public TouristSite searchId(Long id) throws Exception{
        return touristSiteRepository.findById(id).orElseThrow(() -> new Exception("Tourist site not found"));
    }

    // Update a tourist site
    public TouristSite update(TouristSite touristSite) throws Exception{
        searchId(touristSite.getId());
        return touristSiteRepository.save(touristSite);
    }

    // Delete a tourist site
    public void delete(Long id) throws Exception{
        touristSiteRepository.delete(searchId(id));
    }
}
