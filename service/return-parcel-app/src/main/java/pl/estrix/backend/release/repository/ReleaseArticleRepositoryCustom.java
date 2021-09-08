package pl.estrix.backend.release.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;

import java.util.List;

public interface ReleaseArticleRepositoryCustom {


    List<ReleaseArticle> find(ReleaseArticleSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ReleaseArticleSearchCriteriaDto searchCriteria);
}
