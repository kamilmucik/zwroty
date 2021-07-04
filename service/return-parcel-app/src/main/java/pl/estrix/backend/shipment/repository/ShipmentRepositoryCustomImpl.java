package pl.estrix.backend.shipment.repository;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.shipment.dao.QShipment;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.util.List;

@Repository
public class ShipmentRepositoryCustomImpl extends QueryDslRepositorySupportBase implements ShipmentRepositoryCustom {

    private static final QShipment shipment = new QShipment("shipment");

    public ShipmentRepositoryCustomImpl() {
        super(Shipment.class);
    }

    @Override
    public List<Shipment> find(ShipmentSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(shipment.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                Shipment.class,
                shipment.id,
                shipment.number,
                shipment.active,
                shipment.group
        ));
    }

    @Override
    public long findCount(ShipmentSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShipmentSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(shipment);

        if (searchParams.getActive() != null) {
            query.where(shipment.active.eq(searchParams.getActive()));
        }

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                    shipment.number.like("%"+searchParams.getTableSearch()+"%")
            );
        }

        query.where(builder);
        return query;
    }
}
