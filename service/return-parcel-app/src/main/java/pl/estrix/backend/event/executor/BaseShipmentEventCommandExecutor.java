package pl.estrix.backend.event.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.event.dao.ShipmentEvent;
import pl.estrix.backend.event.repository.ShipmentEventRepository;
import pl.estrix.backend.event.repository.ShipmentEventRepositoryCustom;
import pl.estrix.common.dto.ShipmentEventDto;

@Data
public class BaseShipmentEventCommandExecutor extends BaseCommandExecutor<ShipmentEvent, ShipmentEventDto> {

    @Autowired
    protected ShipmentEventRepository repository;

    @Autowired
    protected ShipmentEventRepositoryCustom customRepository;

    @Override
    protected Class<ShipmentEventDto> getDtoClass() {
        return ShipmentEventDto.class;
    }

    public ShipmentEvent mapDtoToEntity(ShipmentEventDto dto) {
        ShipmentEvent entity = new ShipmentEvent();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setCollectorId(dto.getCollectorId());
        entity.setDescription(dto.getDescription());
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setShipmentNumber(dto.getShipmentNumber());
        return entity;
    }

    public ShipmentEventDto mapEntityToDto(ShipmentEvent entity) {
        ShipmentEventDto dto = new ShipmentEventDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setCollectorId(entity.getCollectorId());
        dto.setDescription(entity.getDescription());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setShipmentNumber(entity.getShipmentNumber());
        return dto;
    }

}
