package pl.estrix.backend.release.repository.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.release.dao.QReleaseArticle;
import pl.estrix.backend.release.dao.QReleaseArticlePallet;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.repository.ReleaseArticleRepositoryCustom;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;

import java.util.List;

public class ReleaseArticleRepositoryImpl extends QueryDslRepositorySupportBase implements ReleaseArticleRepositoryCustom {


    private static final QReleaseArticle releaseArticle = new QReleaseArticle("releaseArticle");
    private static final QReleaseArticlePallet releaseArticlePallet = new QReleaseArticlePallet("releaseArticlePallet");

    public ReleaseArticleRepositoryImpl() {
        super(ReleaseArticle.class);
    }

    @Override
    public List<ReleaseArticle> find(ReleaseArticleSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);


        query.orderBy(releaseArticle.releaseDate.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ReleaseArticle.class,
                releaseArticle.id,
                releaseArticle.releaseDate
        ));
    }

    @Override
    public long findCount(ReleaseArticleSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ReleaseArticleSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(releaseArticle);
        query.where(builder);
        return query;
    }
}
