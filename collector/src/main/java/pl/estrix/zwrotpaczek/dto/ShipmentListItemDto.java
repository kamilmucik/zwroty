package pl.estrix.zwrotpaczek.dto;

import java.util.List;

public class ShipmentListItemDto extends BaseEntityDto<Long>{

    private List<ShipmentDto> shipmentsDto;

    public ShipmentListItemDto(List<ShipmentDto> shipmentsDto) {
        this.shipmentsDto = shipmentsDto;
    }
    public ShipmentListItemDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    public List<ShipmentDto> getShipmentsDto() {
        return shipmentsDto;
    }

    public void setShipmentsDto(List<ShipmentDto> shipmentsDto) {
        this.shipmentsDto = shipmentsDto;
    }
}
