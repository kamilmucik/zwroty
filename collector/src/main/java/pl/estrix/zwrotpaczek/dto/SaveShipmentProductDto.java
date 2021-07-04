package pl.estrix.zwrotpaczek.dto;

import java.util.ArrayList;
import java.util.List;

public class SaveShipmentProductDto extends BaseEntityDto<Long> {


    private List<ShipmentProductDto> shipmentProductDtoList = new ArrayList<>();;

    private Long shipmentId;

    public SaveShipmentProductDto() {
    }

    public SaveShipmentProductDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    public SaveShipmentProductDto(List<ShipmentProductDto> shipmentProductDtoList, Long shipmentId) {
        this.shipmentProductDtoList = shipmentProductDtoList;
        this.shipmentId = shipmentId;
    }

    public List<ShipmentProductDto> getShipmentProductDtoList() {
        return shipmentProductDtoList;
    }

    public void setShipmentProductDtoList(List<ShipmentProductDto> shipmentProductDtoList) {
        this.shipmentProductDtoList = shipmentProductDtoList;
    }
    public void addProduct(ShipmentProductDto dto){
        if ( shipmentProductDtoList == null){
            shipmentProductDtoList = new ArrayList<>();
        }
        shipmentProductDtoList.add(dto);
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }
}
