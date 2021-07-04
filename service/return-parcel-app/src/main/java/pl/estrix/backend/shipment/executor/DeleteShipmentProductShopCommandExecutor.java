package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

@Component
public class DeleteShipmentProductShopCommandExecutor extends BaseShipmentProductShopCommandExecutor {

    public void delete(ShipmentProductShopDto dto){
        repository.delete(dto.getId());
    }
    public void deleteByProductId(Long productId){
        repository.deleteByProductId(productId);
    }

    public void deleteByArtReturn(String artReturn){
        repository.deleteByArtReturn(artReturn);
    }

}
