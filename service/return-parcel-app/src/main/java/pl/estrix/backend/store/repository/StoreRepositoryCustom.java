package pl.estrix.backend.store.repository;


import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.store.dao.Store;
import pl.estrix.common.dto.StoreSearchCriteriaDto;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> find(StoreSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(StoreSearchCriteriaDto searchCriteria);
}
