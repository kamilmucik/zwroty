package pl.estrix.zwrotpaczek.controller.dialog.scanlist.model;

import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;

import java.util.ArrayList;
import java.util.List;

public class ScanListFxViewModel {

    private List<ScanListFxImpl> scanFxList = new ArrayList<>();

    public void init() {
        scanFxList.clear();
        int i = 0;
        for (ShipmentProductDto shipmentProductDto : SessionManager.getInstance().getShipmentDetailsDto().getSortShipmentProductDtoList()) {
            scanFxList.add(mapDtoToFx(shipmentProductDto));
            if (i >= 2) break;
            i++;
        }
    }

    private ScanListFxImpl mapDtoToFx(ShipmentProductDto dto) {
        return new ScanListFxImpl(
                dto.getId(),
                dto.getName(),
                dto.getEan(),
                dto.getCounter()
        );
    }

    public List<ScanListFxImpl> getList() {
        return scanFxList;
    }
}
