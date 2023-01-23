package pl.estrix.backend.imageversion.repository.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.group.GroupBy;
import com.mysema.query.jpa.JPAQueryBase;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.backend.imageversion.dao.QProductImageVersion;
import pl.estrix.backend.imageversion.dao.QProductImageVersionRevision;
import pl.estrix.backend.imageversion.repository.ProductImageVersionRepositoryCustom;
import pl.estrix.backend.release.dao.QReleaseArticlePallet;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.backend.release.repository.ReleaseArticlePalletRepositoryCustom;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductImageVersionRepositoryImpl extends QueryDslRepositorySupportBase implements ProductImageVersionRepositoryCustom {

    private static final QProductImageVersion productImageVersion = new QProductImageVersion("productImageVersion");
    private static final QProductImageVersionRevision productImageVersionRevision = new QProductImageVersionRevision("productImageVersionRevision");

    public ProductImageVersionRepositoryImpl() {
        super(ProductImageVersion.class);
    }

    @Override
    public List<ProductImageVersion> find(ProductImageVersionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
//        final JPAQueryBase<?> query = createQuery();
//        https://github.com/querydsl/querydsl/issues/1677
//        JPQLQuery query2 = from(productImageVersion);
//        Map<Long, ProductImageVersion> transform = query2
//                .leftJoin(productImageVersion.revisions, productImageVersionRevision)
//                .transform(GroupBy.groupBy(productImageVersion.id)
//                        .as(Projections.bean(ProductImageVersion.class,
//                                productImageVersion.id,
//                                productImageVersion.ean,
//                                productImageVersion.title,
//                                productImageVersion.artNumber,
//                                GroupBy.list(Projections.bean(ProductImageVersionRevision.class,
//                                                productImageVersionRevision.id,
//                                                productImageVersionRevision.reason).skipNulls())
//                                        .as(productImageVersion.revisions))));
//
//
//
//    return new ArrayList<ProductImageVersion>(transform.values());
        JPQLQuery query = getQueryForFind(searchCriteria);

        query.orderBy(productImageVersion.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);

        return query.list(Projections.bean(
                ProductImageVersion.class,
                productImageVersion.id,
                productImageVersion.ean,
                productImageVersion.title,
                productImageVersion.artNumber
            ));
    }

    @Override
    public long findCount(ProductImageVersionSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ProductImageVersionSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(productImageVersion);

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            builder.and(productImageVersion.ean.like("%"+searchParams.getTableSearch()+"%"))
                    .or(productImageVersion.lastVersionDate.like("%"+searchParams.getTableSearch()+"%"))
                    .or(productImageVersion.artNumber.like("%"+searchParams.getTableSearch()+"%"))
                    .or(productImageVersion.title.like("%"+searchParams.getTableSearch()+"%"));
        }
        query.where(builder);
        return query;
    }
}
