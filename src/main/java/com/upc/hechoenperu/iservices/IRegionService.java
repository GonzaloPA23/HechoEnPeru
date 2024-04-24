package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.entities.Region;

import java.util.List;

public interface IRegionService {
    Region insert(Region region);
    List<Region> list();
    Region searchId(Long id) throws Exception;
    Region update(Region region) throws Exception;
    void delete(Long id) throws Exception;
    Region searchName(String name) throws Exception;
}
