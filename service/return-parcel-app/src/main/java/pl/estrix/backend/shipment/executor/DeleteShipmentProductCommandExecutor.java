package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductDto;

@Component
public class DeleteShipmentProductCommandExecutor extends BaseShipmentProductCommandExecutor {

    public void delete(ShipmentProductDto dto){
        repository.delete(dto.getId());
    }

    public void deleteByArtReturn(String artReturn){
        repository.deleteByArtReturn(artReturn);
    }
}
