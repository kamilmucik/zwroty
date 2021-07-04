package pl.estrix.backend.event.repository;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.event.dao.QShipmentEvent;
import pl.estrix.backend.event.dao.ShipmentEvent;
import pl.estrix.backend.shipment.dao.QShipment;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.repository.ShipmentRepositoryCustom;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ShipmentEventRepositoryCustomImpl extends QueryDslRepositorySupportBase implements ShipmentEventRepositoryCustom {

    private static final QShipmentEvent shipmentEvent = new QShipmentEvent("shipmentEvent");

    public ShipmentEventRepositoryCustomImpl() {
        super(ShipmentEvent.class);
    }

    @Override
    public List<ShipmentEvent> find(ShipmentEventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(shipmentEvent.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ShipmentEvent.class,
                shipmentEvent.id,
                shipmentEvent.collectorId,
                shipmentEvent.description,
                shipmentEvent.username,
                shipmentEvent.shipmentNumber,
                shipmentEvent.lastUpdate
        ));
    }

    @Override
    public long findCount(ShipmentEventSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShipmentEventSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(shipmentEvent);


        LocalDateTime stopDate = LocalDateTime.now();
        LocalDateTime startDate = stopDate.minusDays(30L);

        builder.and(shipmentEvent.lastUpdate.between(startDate,stopDate));

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            builder.and(shipmentEvent.username.like("%"+searchParams.getTableSearch()+"%"))
                    .or(shipmentEvent.description.like("%"+searchParams.getTableSearch()+"%"))
                    .or(shipmentEvent.collectorId.like("%"+searchParams.getTableSearch()+"%"));
        }


        query.where(builder);
        return query;
    }
}
