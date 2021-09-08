package pl.estrix.backend.shipment.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;
import pl.estrix.backend.shipment.repository.ShipmentProductShopRepository;
import pl.estrix.backend.shipment.repository.ShipmentProductShopRepositoryCustom;
import pl.estrix.backend.shipment.repository.ShipmentRepository;
import pl.estrix.backend.shipment.repository.ShipmentRepositoryCustom;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

@Data
public class BaseShipmentProductShopCommandExecutor extends BaseCommandExecutor<ShipmentProductShop, ShipmentProductShopDto> {

    @Autowired
    protected ShipmentProductShopRepository repository;

    @Autowired
    protected ShipmentProductShopRepositoryCustom customRepository;

    @Override
    protected Class<ShipmentProductShopDto> getDtoClass() {
        return ShipmentProductShopDto.class;
    }

    public ShipmentProductShop mapDtoToEntity(ShipmentProductShopDto dto) {
        ShipmentProductShop entity = new ShipmentProductShop();
        entity.setId(dto.getId());
        entity.setProductId(dto.getProductId());
        entity.setShipNumber(dto.getShipNumber());
        entity.setRecognitionCounter(dto.getRecognitionCounter());
        entity.setRecognitionNumber(dto.getRecognitionNumber());
        entity.setShopNumber(dto.getShopNumber());
        entity.setArtNumber(dto.getArtNumber());
        entity.setArtReturn(dto.getArtReturn());
        entity.setScanCorrect(dto.getScanCorrect());
        entity.setScanError(dto.getScanError());
        entity.setScanLabel(dto.getScanLabel());
//        entity.setArchivated(dto.getArchivated());
        return entity;
    }

    public ShipmentProductShopDto mapEntityToDto(ShipmentProductShop entity) {
        ShipmentProductShopDto dto = new ShipmentProductShopDto();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setShipNumber(entity.getShipNumber());
        dto.setRecognitionCounter(entity.getRecognitionCounter());
        dto.setRecognitionNumber(entity.getRecognitionNumber());
        dto.setShopNumber(entity.getShopNumber());
        dto.setArtNumber(entity.getArtNumber());
        dto.setArtReturn(entity.getArtReturn());
        dto.setScanCorrect(entity.getScanCorrect());
        dto.setScanError(entity.getScanError());
        dto.setScanLabel(entity.getScanLabel());
//        dto.setArchivated(entity.getArchivated());
        return dto;
    }

}
