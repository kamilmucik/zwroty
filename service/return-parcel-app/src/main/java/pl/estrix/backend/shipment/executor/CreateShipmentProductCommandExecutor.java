package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductDto;

@Component
public class CreateShipmentProductCommandExecutor extends BaseShipmentProductCommandExecutor{

    public ShipmentProductDto create(ShipmentProductDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
