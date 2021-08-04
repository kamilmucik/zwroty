package pl.estrix.zwrotpaczek.controller.dialog.shoplist.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class SimpleScanListFxListCell <T extends ScanListFx> extends ScanListFxListCell<T> {

    public SimpleScanListFxListCell() {
        getStyleClass().add("simple-media-cell");
        itemProperty().addListener(e -> {
            nameProperty().unbind();
            eanProperty().unbind();
            cntAllProperty().unbind();
            if(getItem() != null) {
                nameProperty().bind(getItem().nameProperty());
                eanProperty().bind(getItem().eanProperty());
                cntAllProperty().bind(getItem().cntAllProperty());
            }
        });


    }

    public static <T extends ScanListFx> Callback<ListView<T>, ListCell<T>> createDefaultCallback() {
        return v -> new SimpleScanListFxListCell<>();
    }
}
