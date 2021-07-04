package pl.estrix.backend.event.repository;


import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.dao.ShipmentEvent;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.util.List;

public interface ShipmentEventRepositoryCustom {

    List<ShipmentEvent> find(ShipmentEventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShipmentEventSearchCriteriaDto searchCriteria);
}
