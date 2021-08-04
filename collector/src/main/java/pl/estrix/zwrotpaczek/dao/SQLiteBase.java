package pl.estrix.zwrotpaczek.dao;

import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;

public class SQLiteBase {

    protected static void addPagingCriteriaToNativeQuery(StringBuilder query, PagingCriteria pagingCriteria) {
        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {
            query.append(" LIMIT " + pagingCriteria.getStart() + ", " + pagingCriteria.getLimit());
        }
    }

    protected static StringBuilder countForNativeQuery(StringBuilder queryBuilder) {
        queryBuilder.insert(0, "SELECT count(*) FROM ( ");
        queryBuilder.append(" ) ");

        return queryBuilder;
    }
}
