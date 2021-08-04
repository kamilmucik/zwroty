package pl.estrix.zwrotpaczek.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ShipmentDetailsDto extends BaseEntityDto<Long>  {

    private CollectorDto collectorDto;

    private ShipmentDto shipmentDto;

    private List<ShipmentProductDto> shipmentProductDtoList;

    public ShipmentDetailsDto() {}

    public ShipmentDetailsDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    public ShipmentDetailsDto(CollectorDto collectorDto, ShipmentDto shipmentDto, List<ShipmentProductDto> shipmentProductDtoList) {
        this.collectorDto = collectorDto;
        this.shipmentDto = shipmentDto;
        this.shipmentProductDtoList = shipmentProductDtoList;
    }

    public void setLabel(String label) {
        super.setLabel(label);
    }

    public ShipmentDto getShipmentDto() {
        return shipmentDto;
    }

    public CollectorDto getCollectorDto() {
        return collectorDto;
    }

    public void setCollectorDto(CollectorDto collectorDto) {
        this.collectorDto = collectorDto;
    }

    public void setShipmentDto(ShipmentDto shipmentDto) {
        this.shipmentDto = shipmentDto;
    }

    public List<ShipmentProductDto> getShipmentProductDtoList() {
        return shipmentProductDtoList;
    }
    public List<ShipmentProductDto> getSortShipmentProductDtoList() {
        Collections.sort(shipmentProductDtoList, new Comparator<ShipmentProductDto>(){
            @Override
            public int compare(ShipmentProductDto o1, ShipmentProductDto o2) {
                if (o1.getUpdate() == null || o2.getUpdate() == null)
                    return 0;
                return o2.getUpdate().compareTo(o1.getUpdate());
            }
        });
        return shipmentProductDtoList;
    }

    public void setShipmentProductDtoList(List<ShipmentProductDto> shipmentProductDtoList) {
        this.shipmentProductDtoList = shipmentProductDtoList;
    }
}
