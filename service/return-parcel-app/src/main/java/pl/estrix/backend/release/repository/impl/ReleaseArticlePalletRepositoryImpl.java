package pl.estrix.backend.release.repository.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.release.dao.QReleaseArticle;
import pl.estrix.backend.release.dao.QReleaseArticlePallet;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.backend.release.repository.ReleaseArticlePalletRepositoryCustom;
import pl.estrix.backend.release.repository.ReleaseArticleRepositoryCustom;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReleaseArticlePalletRepositoryImpl extends QueryDslRepositorySupportBase implements ReleaseArticlePalletRepositoryCustom {


//    private static final QReleaseArticle releaseArticle = new QReleaseArticle("releaseArticle");
    private static final QReleaseArticlePallet releaseArticlePallet = new QReleaseArticlePallet("releaseArticlePallet");

    public ReleaseArticlePalletRepositoryImpl() {
        super(ReleaseArticlePallet.class);
    }

    @Override
    public List<ReleaseArticlePallet> find(ReleaseArticlePalletSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);

        query.orderBy(releaseArticlePallet.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ReleaseArticlePallet.class,
                releaseArticlePallet.id,
                releaseArticlePallet.releaseCode,
                releaseArticlePallet.artNumber,
                releaseArticlePallet.counter,
                releaseArticlePallet.palletFlag,
                releaseArticlePallet.productEan,
                releaseArticlePallet.palletCounter,
                releaseArticlePallet.returnNumber
        ));
    }

    @Override
    public long findCount(ReleaseArticlePalletSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ReleaseArticlePalletSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(releaseArticlePallet);

        if (searchParams.getReleaseArticleId() != null ){

            builder.and(
                    releaseArticlePallet.releaseArticle.id.eq(searchParams.getReleaseArticleId())
            );
        }
        query.where(builder);
        return query;
    }
}
