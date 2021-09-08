package pl.estrix.backend.event.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.shipment.executor.BaseShipmentCommandExecutor;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.model.ShipmentDto;

@Component
public class CreateShipmentEventCommandExecutor extends BaseShipmentEventCommandExecutor {

    public ShipmentEventDto create(ShipmentEventDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
