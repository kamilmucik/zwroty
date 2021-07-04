package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

@Component
public class CreateShipmentProductShopCommandExecutor extends BaseShipmentProductShopCommandExecutor {

    public ShipmentProductShopDto create(ShipmentProductShopDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
