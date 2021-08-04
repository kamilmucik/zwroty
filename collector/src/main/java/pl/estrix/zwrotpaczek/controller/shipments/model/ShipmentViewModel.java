package pl.estrix.zwrotpaczek.controller.shipments.model;

import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.dto.ShipmentDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;

import java.util.ArrayList;
import java.util.List;

public class ShipmentViewModel {

    private static List<ShipmentsFxImpl> scanFxList = new ArrayList<>();

    public void init() {
        scanFxList.clear();
       for (ShipmentDto shipmentDto : SessionManager.getInstance().getShipmentListItemDtoList().getShipmentsDto()) {
            scanFxList.add(mapDtoToFx(shipmentDto));
        }
    }

    private ShipmentsFxImpl mapDtoToFx(ShipmentDto dto) {
        return new ShipmentsFxImpl(
                dto.getId(),
                dto.getNumber().toString(),
                dto.getProductCounter().longValue()
        );
    }

    public List<ShipmentsFxImpl> getList() {
        return scanFxList;
    }
}
