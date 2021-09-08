package pl.estrix.backend.core.dao;

import pl.estrix.backend.base.PagingCriteria;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import static pl.estrix.backend.core.QueryDslRepositorySupportBase.DEFAULT_LIMIT;

public abstract class QueryUtils {

    private static final String PATTERN_CAN_MATCH_ZERO_OR_MORE = "%";

    /**
     * Add left pattern, can match zero or more, for like query.
     *
     * @param value
     *        value for add pattern.
     * @return value with pattern on left or null for null value.
     */
    public static String addLeftPatZeroOrMore(String value) {

        if (value == null) {
            return null;
        }

        return PATTERN_CAN_MATCH_ZERO_OR_MORE + value;
    }

    /**
     * Add right pattern, can match zero or more, for like query.
     *
     * @param value
     *        value for add pattern.
     * @return value with pattern on right or null for null value.
     */
    public static String addRightPatZeroOrMore(String value) {

        if (value == null) {
            return null;
        }

        return value + PATTERN_CAN_MATCH_ZERO_OR_MORE;
    }

    /**
     * Add both side pattern, can match zero or more, for like query.
     *
     * @param value
     *        value for add pattern.
     * @return value with pattern on both side or null for null value.
     */
    public static String addBothPatZeroOrMore(String value) {

        if (value == null) {
            return null;
        }

        return PATTERN_CAN_MATCH_ZERO_OR_MORE + value + PATTERN_CAN_MATCH_ZERO_OR_MORE;
    }

    /**
     * Add paging criteria to query (ex. for native query)
     *
     * @param query          - query to paging
     * @param pagingCriteria - criteria to paging
     */
    public static void addPagingCriteria(Query query, PagingCriteria pagingCriteria) {
        query.setMaxResults(DEFAULT_LIMIT);
        if (pagingCriteria != null && pagingCriteria.getLimit() != null) {
            query.setFirstResult(pagingCriteria.getStart());
            query.setMaxResults(pagingCriteria.getLimit());
        }
    }

    /**
     * Add named parameters to query (ex. for native query)
     *
     * @param query      - guery with parameters
     * @param parameters - parameters for query
     */
    public static void addParameterToQuery(Query query, Map<String, Object> parameters) {
        parameters.keySet().forEach(p -> query.setParameter(p, parameters.get(p)));
    }

    /**
     * Adds timeout hint to query
     * @param query - query to paging
     * @param timeout - value
     * @param unit - time unit
     */
    public static void setTimeoutHint(Query query, int timeout, TimeUnit unit) {
        query.setHint("javax.persistence.query.timeout", (int)TimeUnit.MILLISECONDS.convert(timeout, unit));
    }

    /**
     * Workaround for wrong using of {@link Query#getSingleResult()}
     * (when there's no results or more than one result).
     *
     * @param query query
     * @return single first result of query
     *
     * @see <a href="http://stackoverflow.com/a/2003015">http://stackoverflow.com/a/2003015</a>
     * @see <a href="http://sysout.be/2011/03/09/why-you-should-never-use-getsingleresult-in-jpa/">
     *     http://sysout.be/2011/03/09/why-you-should-never-use-getsingleresult-in-jpa/</a>
     *
     */
    public static Optional<Object> getSingleResult(final Query query){
        final List results = query.getResultList();
        return (results.isEmpty()) ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

}