package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ShipmentsFxList <T extends ShipmentsFx> extends ListView<T> {

    public ShipmentsFxList() {
        setCellFactory(SimpleShipmentsFxListCell.createDefaultCallback());
    }

    public ShipmentsFxList(ObservableList<T> items) {
        super(items);
        setCellFactory(SimpleShipmentsFxListCell.createDefaultCallback());
    }
}
