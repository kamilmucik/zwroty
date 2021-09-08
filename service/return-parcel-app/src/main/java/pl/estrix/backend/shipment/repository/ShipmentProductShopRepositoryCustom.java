package pl.estrix.backend.shipment.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.util.List;

public interface ShipmentProductShopRepositoryCustom {


    List<ShipmentProductShop> find(ShipmentProductShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShipmentProductShopSearchCriteriaDto searchCriteria);
}
