package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.controller.shipments.ShipmentsListController;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductShopDao;
import pl.estrix.zwrotpaczek.dto.*;
import pl.estrix.zwrotpaczek.service.*;
import pl.estrix.zwrotpaczek.ui.RoundImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class SimpleShipmentsFxListCell <T extends ShipmentsFx> extends ShipmentsFxListCell<T>  {

    private RoundImageView imageView;

    private SimpleShipmentsFxListCell() {
        imageView = new RoundImageView(64.0);
        getStyleClass().add("simple-media-cell");

        itemProperty().addListener(e -> {
            nameProperty().unbind();
            productCntrProperty().unbind();
            if(getItem() != null) {
                getItem().switchedOnProperty().setValue(false);
                nameProperty().bind(getItem().nameProperty());
                productCntrProperty().bind(getItem().productCntrProperty());
                imageView.imageProperty().bind(getItem().imageProperty());
                imageView.setOnMousePressed(event -> {
                    ShipmentsListController.getInstance().setBusy(true);
                    ShipmentsListController.getInstance().setInfo("Pobieram: " + getItem().nameProperty().get());
                    downloadShipment(getItem().nameProperty().get());
                });
            } else {
                return;
            }
        });

        getCenterContent().setOnMousePressed( event -> {
            imageView.restart();
            getCenterContent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .setDisable(true);
            for( Node node : getCenterContent()
                    .getParent()
                    .getParent()
                    .getChildrenUnmodifiable()){
                SimpleShipmentsFxListCell cell = ( SimpleShipmentsFxListCell) node;

                cell.setLeftContent(null);
                cell.getStyleClass().remove("simple-media-cell-select");

            }
            imageView.restart();
            setLeftContent(imageView);
            getStyleClass().add("simple-media-cell-select");

            getCenterContent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .setDisable(false);
        });
    }

    static <T extends ShipmentsFx> Callback<ListView<T>, ListCell<T>> createDefaultCallback() {
        return v -> new SimpleShipmentsFxListCell<>();
    }

    private void downloadShipment(String id){
        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");
        SettingDao setting = null;
        if (!settingDao.isPresent()) {
            setting = new SettingDao(null,"shipment_id",id,new Date());
        } else{
            setting = settingDao.get();
        }
        setting.setValue(getItem().nameProperty().getValue());
        SQLiteSettingService.getInstance().save(setting);

        RootController.getInstance().openScan(new ViewParam(id));

    }
}
