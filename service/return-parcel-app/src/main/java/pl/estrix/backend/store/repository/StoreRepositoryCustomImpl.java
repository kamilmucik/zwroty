package pl.estrix.backend.store.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.category.dao.QCategoryGroup;
import pl.estrix.backend.core.dao.QueryUtils;
import pl.estrix.backend.store.dao.QStore;
import pl.estrix.backend.store.dao.Store;
import pl.estrix.common.dto.StoreSearchCriteriaDto;

import javax.persistence.Query;
import java.util.List;

@Repository
public class StoreRepositoryCustomImpl extends QueryDslRepositorySupportBase implements StoreRepositoryCustom{

    private static final QStore store = new QStore("store");

    public StoreRepositoryCustomImpl() {
        super(Store.class);
    }

    @Override
    public List<Store> find(StoreSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(store.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                Store.class,
                store.id,
                store.place,
                store.minVolume,
                store.maxVolume,
                store.group,
                store.description
        ));
    }

    @Override
    public long findCount(StoreSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(StoreSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(store);

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                store.place.contains(searchParams.getTableSearch())
                .or(store.description.contains(searchParams.getTableSearch()))
                .or(store.minVolume.like("%"+searchParams.getTableSearch()+"%"))
                .or(store.maxVolume.like("%"+searchParams.getTableSearch()+"%"))
            );
        }

        query.where(builder);
        return query;
    }
}
