package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentDto;

@Component
public class UpdateShipmentCommandExecutor extends BaseShipmentCommandExecutor {

    public ShipmentDto update(ShipmentDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
