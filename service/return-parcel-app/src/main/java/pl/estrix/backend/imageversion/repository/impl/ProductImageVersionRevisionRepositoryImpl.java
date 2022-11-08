package pl.estrix.backend.imageversion.repository.impl;

import com.mysema.query.BooleanBuilder;
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
import pl.estrix.backend.imageversion.repository.ProductImageVersionRevisionRepositoryCustom;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.estrix.backend.imageversion.dao.QProductImageVersion.productImageVersion;

public class ProductImageVersionRevisionRepositoryImpl extends QueryDslRepositorySupportBase implements ProductImageVersionRevisionRepositoryCustom {

    private static final QProductImageVersionRevision productImageVersionRevision = new QProductImageVersionRevision("productImageVersionRevision");

    public ProductImageVersionRevisionRepositoryImpl() {
        super(ProductImageVersionRevision.class);
    }

    @Override
    public List<ProductImageVersionRevision> find(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
//        final JPAQueryBase<?> query = createQuery();
//        https://github.com/querydsl/querydsl/issues/1677
//        JPQLQuery query2 = from(productImageVersionRevision);
//        Map<Long, ProductImageVersion> transform = query2
//                .leftJoin(productImageVersionRevision.revisions, productImageVersionRevision)
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
//    return new ArrayList<ProductImageVersion>(transform.values());
        JPQLQuery query = getQueryForFind(searchCriteria);

        query.orderBy(productImageVersionRevision.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);

        return query.list(Projections.bean(
                ProductImageVersionRevision.class,
                productImageVersionRevision.id,
                productImageVersionRevision.releaseDate,
                productImageVersionRevision.reason,
                productImageVersionRevision.imgFrontBase64,
                productImageVersionRevision.imgBackBase64
            ));
    }

    @Override
    public long findCount(ProductImageVersionRevisionSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ProductImageVersionRevisionSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(productImageVersionRevision);

        if (Objects.nonNull(searchParams.getVersionId()) && searchParams.getVersionId() > 0) {
            builder.and(productImageVersionRevision.productImageVersion.id.eq(searchParams.getVersionId()));
        }
        query.where(builder);
        return query;
    }
}
