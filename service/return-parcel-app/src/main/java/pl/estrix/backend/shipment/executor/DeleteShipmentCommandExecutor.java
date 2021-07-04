package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentDto;

@Component
public class DeleteShipmentCommandExecutor extends BaseShipmentCommandExecutor {
    public void delete(ShipmentDto storeDto){
        repository.delete(storeDto.getId());
    }
}
