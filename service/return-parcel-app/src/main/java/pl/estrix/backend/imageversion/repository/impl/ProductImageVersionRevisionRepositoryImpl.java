package pl.estrix.backend.imageversion.repository.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.category.dao.Category;
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

    private static final QProductImageVersion productImageVersion = new QProductImageVersion("productImageVersion");
    public ProductImageVersionRevisionRepositoryImpl() {
        super(ProductImageVersionRevision.class);
    }

    @Override
    public List<ProductImageVersionRevision> find(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);

        query.orderBy(SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())?productImageVersionRevision.id.asc():productImageVersionRevision.id.desc());

        addPagingCriteriaToQuery(query, pagingCriteria);

        return query.list(Projections.bean(
                ProductImageVersionRevision.class,
                productImageVersionRevision.id,
                productImageVersionRevision.description,
                productImageVersionRevision.imgPath,
                productImageVersionRevision.main,
                productImageVersionRevision.lastUpdate,
                productImageVersionRevision.hashGroup

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
        if (Objects.nonNull(searchParams.getVersionRevisionId()) && searchParams.getVersionRevisionId() > 0) {
            builder.and(productImageVersionRevision.id.eq(searchParams.getVersionRevisionId()));
        }
        if (Objects.nonNull(searchParams.getTableSearch())) {
            builder.and(productImageVersionRevision.hashGroup.eq(searchParams.getTableSearch()));
        }
        if (Objects.nonNull(searchParams.getMainOnly()) && searchParams.getMainOnly()) {
            builder.and(productImageVersionRevision.main.eq(searchParams.getMainOnly()));
        }
        query.where(builder);
        return query;
    }
}
