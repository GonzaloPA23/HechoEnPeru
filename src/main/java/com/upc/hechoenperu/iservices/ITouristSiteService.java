package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.entities.TouristSite;

import java.util.List;

public interface ITouristSiteService {
    TouristSite insert(TouristSite touristSite);
    List<TouristSite> list();
    TouristSite searchId(Long id) throws Exception;
    TouristSite update(TouristSite touristSite) throws Exception;
    TouristSite updateWithValidation(TouristSite touristSite);
    void delete(Long id) throws Exception;
    List<TouristSite> listTouristSitesByPage(int offset, int limit);
}
