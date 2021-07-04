package pl.estrix.backend.shipment.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.ShipmentProduct;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentProductSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.log.Timed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReadShipmentProductCommandExecutor extends BaseShipmentProductCommandExecutor{

    public Integer countByShipmentId(Long shipmntId) {
        return repository.countByShipmentId(shipmntId);
    }

    @Timed
    public ListResponseDto<ShipmentProductDto> find(ShipmentProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria, boolean fillShops) {
        List<ShipmentProduct> result = customRepository.find(searchCriteria, pagingCriteria);
        List<ShipmentProductDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (fillShops) {
            queryResultList.stream().forEach(o -> {
                o.setShopCounter(shopRepositoryCustom.findCount(ShipmentProductShopSearchCriteriaDto
                        .builder()
                        .productId(o.getId())
                        .build()));
            });
        }

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }

    public ShipmentProductDto getShipmentProductDto(Long shipmentNumber, String productEAN){
        ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                .builder()
                .ean(productEAN)
                .shipmentId(shipmentNumber)
                .build();
        Optional<ShipmentProductDto> result = customRepository.find(searchCriteria, null)
                .stream()
                .map(this::mapEntityToDto).findFirst();
        return result.isPresent()?result.get():null;
    }

    public ShipmentProductDto getShipmentProductDto(Long shipmentNumber, Long artNumber){
        ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                .builder()
                .artNumber(artNumber)
                .shipmentId(shipmentNumber)
                .build();
        Optional<ShipmentProductDto> result = customRepository.find(searchCriteria, null)
                .stream()
                .map(this::mapEntityToDto).findFirst();
        return result.isPresent()?result.get():null;
    }

    public ShipmentProductDto getShipmentProductDto(String artReturn, Long artNumber){
        ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                .builder()
                .artNumber(artNumber)
                .artReturn(artReturn)
                .shipmentId(0L)
                .build();
        Optional<ShipmentProductDto> result = customRepository.find(searchCriteria, null)
                .stream()
                .map(this::mapEntityToDto).findFirst();
        return result.isPresent()?result.get():null;
    }
    public ShipmentProductDto getShipmentProductDto(String artReturn, String ean){
        ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                .builder()
                .ean(ean)
                .artReturn(artReturn)
                .shipmentId(0L)
                .build();
        Optional<ShipmentProductDto> result = customRepository.find(searchCriteria, null)
                .stream()
                .map(this::mapEntityToDto).findFirst();
        return result.isPresent()?result.get():null;
    }
}
