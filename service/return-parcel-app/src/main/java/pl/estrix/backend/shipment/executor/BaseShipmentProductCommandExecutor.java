package pl.estrix.backend.shipment.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.shipment.dao.ShipmentProduct;
import pl.estrix.backend.shipment.repository.ShipmentProductRepository;
import pl.estrix.backend.shipment.repository.ShipmentProductRepositoryCustom;
import pl.estrix.backend.shipment.repository.ShipmentProductShopRepositoryCustom;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.time.LocalDateTime;

@Data
public class BaseShipmentProductCommandExecutor extends BaseCommandExecutor<ShipmentProduct, ShipmentProductDto> {

    @Autowired
    protected ShipmentProductRepository repository;

    @Autowired
    protected ShipmentProductRepositoryCustom customRepository;

    @Autowired
    protected ShipmentProductShopRepositoryCustom shopRepositoryCustom;

    @Override
    protected Class<ShipmentProductDto> getDtoClass() {
        return ShipmentProductDto.class;
    }

    public ShipmentProduct mapDtoToEntity(ShipmentProductDto dto) {
        ShipmentProduct entity = new ShipmentProduct();
        entity.setId(dto.getId());
        entity.setArtNumber(dto.getArtNumber());
        entity.setName(dto.getName());
        entity.setCounter(dto.getCounter());
        entity.setCompany(dto.getCompanyName());
        entity.setArtReturn(dto.getArtReturn());
        entity.setWeight(dto.getWeight());
        entity.setArtVolume(dto.getArtValume());
        entity.setEan(dto.getEan());
        entity.setShipmentId(dto.getShipmentId());
        entity.setScanCorrect(dto.getScanCorrect());
        entity.setScanError(dto.getScanError());
        entity.setScanLabel(dto.getScanLabel());
        entity.setScanUtilization(dto.getScanUtilization());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setScanLog(dto.getScanLog());
        return entity;
    }

    public ShipmentProductDto mapEntityToDto(ShipmentProduct entity) {
        ShipmentProductDto dto = new ShipmentProductDto();
        dto.setId(entity.getId());
        dto.setArtNumber(entity.getArtNumber());
        dto.setName(entity.getName());
        dto.setCounter(entity.getCounter());
        dto.setCompanyName(entity.getCompany());
        dto.setArtReturn(entity.getArtReturn());
        dto.setWeight(entity.getWeight());
        dto.setArtValume(entity.getArtVolume());
        dto.setEan(entity.getEan());
        dto.setShipmentId(entity.getShipmentId());
        dto.setScanCorrect(entity.getScanCorrect());
        dto.setScanError(entity.getScanError());
        dto.setScanLabel(entity.getScanLabel());
        dto.setScanUtilization(entity.getScanUtilization());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setScanLog(entity.getScanLog());

        return dto;
    }

}
