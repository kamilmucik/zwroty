package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadShipmentProductShopCommandExecutor extends BaseShipmentProductShopCommandExecutor {

    public ListResponseDto<ShipmentProductShopDto> find(ShipmentProductShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ShipmentProductShop> result = customRepository.find(searchCriteria, pagingCriteria);
        List<ShipmentProductShopDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }
}
