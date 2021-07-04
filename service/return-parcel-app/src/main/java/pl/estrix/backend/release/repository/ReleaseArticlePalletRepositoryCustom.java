package pl.estrix.backend.release.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;

import java.util.List;

public interface ReleaseArticlePalletRepositoryCustom {

    List<ReleaseArticlePallet> find(ReleaseArticlePalletSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ReleaseArticlePalletSearchCriteriaDto searchCriteria);
}
