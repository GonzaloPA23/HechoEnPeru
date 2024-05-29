package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.dtos.response.LocalCraftsmenByOffsetLimitResponseDTO;
import com.upc.hechoenperu.entities.LocalCraftsman;

import java.util.List;

public interface ILocalCrastmanService {
    LocalCraftsman insert(LocalCraftsman localCraftsman);
    List<LocalCraftsman> list();
    LocalCraftsman searchId(Long id) throws Exception;
    LocalCraftsman update(LocalCraftsman localCraftsman) throws Exception;
    LocalCraftsman updateWithValidations(LocalCraftsman localCraftsman) throws Exception;
    void delete(Long id) throws Exception;
    List<LocalCraftsman> listLocalCraftsmenByPageModeUser(int offset, int limit);
    List<LocalCraftsman> listLocalCraftsmenByPageModeAdmin(int offset, int limit);
    List<LocalCraftsman> listLocalCraftsmenByRegion(Long id);
}
