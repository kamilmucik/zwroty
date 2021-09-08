package pl.estrix.backend.shipment.repository;


import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.util.List;

public interface ShipmentRepositoryCustom {

    List<Shipment> find(ShipmentSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShipmentSearchCriteriaDto searchCriteria);
}
