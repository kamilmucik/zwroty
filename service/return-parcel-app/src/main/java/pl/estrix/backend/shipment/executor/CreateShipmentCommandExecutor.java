package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentDto;

@Component
public class CreateShipmentCommandExecutor extends BaseShipmentCommandExecutor {

    public ShipmentDto create(ShipmentDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
