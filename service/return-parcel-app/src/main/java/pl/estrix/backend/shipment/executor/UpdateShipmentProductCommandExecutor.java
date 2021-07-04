package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductDto;

@Component
public class UpdateShipmentProductCommandExecutor extends BaseShipmentProductCommandExecutor{

    public ShipmentProductDto update(ShipmentProductDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
