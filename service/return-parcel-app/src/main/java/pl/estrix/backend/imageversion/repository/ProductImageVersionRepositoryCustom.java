package pl.estrix.backend.imageversion.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;

import java.util.List;

public interface ProductImageVersionRepositoryCustom {

    List<ProductImageVersion> find(ProductImageVersionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ProductImageVersionSearchCriteriaDto searchCriteria);
}
