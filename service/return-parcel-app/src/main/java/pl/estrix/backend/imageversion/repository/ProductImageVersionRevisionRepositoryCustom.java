package pl.estrix.backend.imageversion.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;

import java.util.List;

public interface ProductImageVersionRevisionRepositoryCustom {

    List<ProductImageVersionRevision> find(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ProductImageVersionRevisionSearchCriteriaDto searchCriteria);
}
