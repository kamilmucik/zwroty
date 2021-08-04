package pl.estrix.zwrotpaczek.controller.scan.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ScanProductFxList <T extends ScanProductFx> extends ListView<T> {

    public ScanProductFxList() {
        setCellFactory(SimpleScanProductFxListCell.createDefaultCallback());
    }

    public ScanProductFxList(ObservableList<T> items) {
        super(items);
        setCellFactory(SimpleScanProductFxListCell.createDefaultCallback());
    }
}
