package pl.estrix.backend.collector.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.collector.dao.Collector;
import pl.estrix.backend.collector.dao.QCollector;
import pl.estrix.common.dto.CollectorSearchCriteriaDto;

import java.util.List;

@Repository
public class CollectorRepositoryCustomImpl extends QueryDslRepositorySupportBase implements CollectorRepositoryCustom {

    private static final QCollector collector = new QCollector("collector");

    public CollectorRepositoryCustomImpl() {
        super(Collector.class);
    }

    @Override
    public List<Collector> find(CollectorSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(collector.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                Collector.class,
                collector.id,
                collector.number,
                collector.sessionId
        ));
    }

    @Override
    public long findCount(CollectorSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(CollectorSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(collector);

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                    collector.number.like("%"+searchParams.getTableSearch()+"%")
            );
        }

        query.where(builder);
        return query;
    }
}
