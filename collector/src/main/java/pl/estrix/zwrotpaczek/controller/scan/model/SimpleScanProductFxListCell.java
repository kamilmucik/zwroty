package pl.estrix.zwrotpaczek.controller.scan.model;

import javafx.scene.Cursor;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pl.estrix.zwrotpaczek.ui.RoundImageView;

public class SimpleScanProductFxListCell <T extends ScanProductFx> extends ScanProductFxListCell<T> {

    private RoundImageView imageView;
    private RoundImageView imageStatusView;

    public SimpleScanProductFxListCell() {
        imageView = new RoundImageView(32.0);
//        setRightContent(imageView);

        getStyleClass().add("simple-media-cell");
        itemProperty().addListener(e -> {
            nameProperty().unbind();
            eanProperty().unbind();
            storeProperty().unbind();
            cntAllProperty().unbind();
            cntScanErrorProperty().unbind();
            cntScanCorrectProperty().unbind();
            imageView.imageProperty().unbind();
            if(getItem() != null) {
                nameProperty().bind(getItem().nameProperty());
                eanProperty().bind(getItem().eanProperty());
                storeProperty().bind(getItem().storeProperty());
                cntAllProperty().bind(getItem().cntAllProperty());
                cntScanErrorProperty().bind(getItem().cntErrorProperty());
                cntScanCorrectProperty().bind(getItem().cntCorrectProperty());
                imageView.imageProperty().bind(getItem().imageProperty());
            }
        });

        imageView.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                imageView.setCursor(Cursor.HAND);
            }
        });
        imageView.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                imageView.setCursor(Cursor.DEFAULT);
            }
        });

    }

    public static <T extends ScanProductFx> Callback<ListView<T>, ListCell<T>> createDefaultCallback() {
        return v -> new SimpleScanProductFxListCell<>();
    }
}
