package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

@Component
public class UpdateShipmentProductShopCommandExecutor extends BaseShipmentProductShopCommandExecutor {

    public ShipmentProductShopDto update(ShipmentProductShopDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
