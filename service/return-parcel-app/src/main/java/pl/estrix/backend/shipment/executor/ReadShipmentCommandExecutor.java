package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.log.Timed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadShipmentCommandExecutor extends BaseShipmentCommandExecutor {

    public ListResponseDto<ShipmentDto> find(ShipmentSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<Shipment> result = customRepository.find(searchCriteria, pagingCriteria);
        List<ShipmentDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }

    @Timed
    public ShipmentDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public ShipmentDto findByNumber(String number) {
        return mapEntityToDto(repository.findByNumber(number));
    }
}
