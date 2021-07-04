package pl.estrix.backend.shipment.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.event.repository.ShipmentEventRepository;
import pl.estrix.backend.event.repository.ShipmentEventRepositoryCustom;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.repository.ShipmentRepository;
import pl.estrix.backend.shipment.repository.ShipmentRepositoryCustom;
import pl.estrix.common.dto.model.ShipmentDto;

@Data
public class BaseShipmentCommandExecutor extends BaseCommandExecutor<Shipment, ShipmentDto> {

    @Autowired
    protected ShipmentRepository repository;

    @Autowired
    protected ShipmentRepositoryCustom customRepository;

    @Override
    protected Class<ShipmentDto> getDtoClass() {
        return ShipmentDto.class;
    }

    public Shipment mapDtoToEntity(ShipmentDto dto) {
        Shipment entity = new Shipment();
        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        entity.setActive(dto.getActive());
        entity.setGroup(dto.getGroup());
        return entity;
    }

    public ShipmentDto mapEntityToDto(Shipment entity) {
        ShipmentDto dto = new ShipmentDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setActive(entity.getActive());
        dto.setGroup(entity.getGroup());
        return dto;
    }

}
