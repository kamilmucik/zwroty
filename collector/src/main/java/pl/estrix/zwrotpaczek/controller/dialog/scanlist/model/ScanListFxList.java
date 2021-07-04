package pl.estrix.zwrotpaczek.controller.dialog.scanlist.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ScanListFxList <T extends ScanListFx> extends ListView<T> {

    public ScanListFxList() {
        setCellFactory(SimpleScanListFxListCell.createDefaultCallback());
    }

    public ScanListFxList(ObservableList<T> items) {
        super(items);
        setCellFactory(SimpleScanListFxListCell.createDefaultCallback());
    }
}
