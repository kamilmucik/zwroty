package pl.estrix.backend.collector.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.collector.dao.Collector;
import pl.estrix.common.dto.CollectorSearchCriteriaDto;

import java.util.List;

public interface CollectorRepositoryCustom {

    List<Collector> find(CollectorSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(CollectorSearchCriteriaDto searchCriteria);

}
