package pl.estrix.backend.event.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.dao.ShipmentEvent;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.executor.BaseShipmentCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadShipmentEventCommandExecutor extends BaseShipmentEventCommandExecutor {

    public ListResponseDto<ShipmentEventDto> find(ShipmentEventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ShipmentEvent> result = customRepository.find(searchCriteria, pagingCriteria);
        List<ShipmentEventDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }

    public ShipmentEventDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
}
