package pl.estrix.backend.base;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.path.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Optional;


public class QueryDslRepositorySupportBase extends QueryDslRepositorySupport {

    public static final int DEFAULT_LIMIT = 5000;

    public QueryDslRepositorySupportBase(Class<?> domainClass) {
        super(domainClass);
    }

    private boolean isEmptyString(Object id) {
        return id instanceof String && StringUtils.isBlank((String) id);
    }

    protected void addPagingCriteriaToQuery(JPQLQuery query, PagingCriteria pagingCriteria) {

        query.limit(DEFAULT_LIMIT);

        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {

            query.offset(pagingCriteria.getStart());
            query.limit(pagingCriteria.getLimit());
        }
    }

    protected void addPagingCriteriaToQuery(JPQLQuery query, PagingCriteria pagingCriteria, StringPath stringPath) {

        query.limit(DEFAULT_LIMIT);

        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {

            query.offset(pagingCriteria.getStart());
            query.orderBy(stringPath.asc());
            query.limit(pagingCriteria.getLimit());
        }
    }

    protected void addPagingCriteriaToNativeQuery(StringBuilder query, PagingCriteria pagingCriteria) {
        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {
            query.append(" OFFSET " + pagingCriteria.getStart() + " ROWS FETCH NEXT " + pagingCriteria.getLimit() + " ROWS ONLY ");
        }
    }

    protected StringBuilder countForNativeQuery(StringBuilder queryBuilder) {
        queryBuilder.insert(0, "SELECT count(*) FROM ( ");
        queryBuilder.append(" ) ");

        return queryBuilder;
    }

    protected void addPagingCriteriaToNativeQuery(Query query, PagingCriteria pagingCriteria) {
        query.setMaxResults(DEFAULT_LIMIT);

        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {
            query.setFirstResult(pagingCriteria.getStart());
            query.setMaxResults(pagingCriteria.getLimit());
        }
    }

    protected void clearEmptyIdOuterJoinLists(Collection<? extends BaseEntityDto> collection) {
        Optional<? extends BaseEntityDto> nullObject =
                collection.stream().filter(entity -> entity.getId() == null || isEmptyString(entity.getId())).findAny();
        if (nullObject.isPresent()) {
            collection.clear();
        }
    }
}
