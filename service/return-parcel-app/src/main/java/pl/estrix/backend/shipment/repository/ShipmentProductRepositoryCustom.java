package pl.estrix.backend.shipment.repository;


import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.ShipmentProduct;
import pl.estrix.common.dto.ShipmentProductSearchCriteriaDto;

import java.util.List;

public interface ShipmentProductRepositoryCustom {

    List<ShipmentProduct> find(ShipmentProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShipmentProductSearchCriteriaDto searchCriteria);
}
