package pl.estrix.zwrotpaczek.controller.scan.model;

import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;

import java.util.ArrayList;
import java.util.List;

public class ScanViewModel {

    private List<ScanProductFxImpl> scanFxList = new ArrayList<>();

    public void init() {
        scanFxList.clear();
        int i = 0;
        for (ShipmentProductDto shipmentProductDto : SessionManager.getInstance().getShipmentDetailsDto().getSortShipmentProductDtoList()) {
            scanFxList.add(mapDtoToFx(shipmentProductDto));
            if (i >= 2) break;
            i++;
        }
    }

    private ScanProductFxImpl mapDtoToFx(ShipmentProductDto dto) {
        return new ScanProductFxImpl(
                dto.getId(),
                dto.getName(),
                dto.getEan(),
                dto.getCounter(),
                dto.getScanCorrect(),
                dto.getScanError(),
                dto.getStore()
        );
    }

    public List<ScanProductFxImpl> getList() {
        return scanFxList;
    }
}
