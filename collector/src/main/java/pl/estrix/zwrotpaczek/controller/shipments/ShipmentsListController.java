package pl.estrix.zwrotpaczek.controller.shipments;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.controller.shipments.model.ShipmentViewModel;
import pl.estrix.zwrotpaczek.controller.shipments.model.ShipmentsFx;
import pl.estrix.zwrotpaczek.controller.shipments.model.ShipmentsFxList;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dto.ShipmentDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;
import pl.estrix.zwrotpaczek.service.RestService;
import pl.estrix.zwrotpaczek.service.SQLiteSettingService;
import pl.estrix.zwrotpaczek.service.SQLiteShipmentProductService;
import pl.estrix.zwrotpaczek.service.ShipmentDownloadUtil;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ShipmentsListController implements Initializable  {

    private static ShipmentsListController instance = null;

    @FXML
    private Text info;

    @FXML
    private Label timerLabel;

    @FXML
    private AnchorPane listAnchorPane;

    @FXML
    private ImageView imageLoad;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Parent contentPane;

    private ShipmentDownloadUtil shipmentDownloadUtil;

    private static ShipmentsFxList<ShipmentsFx> lastScanedList = new ShipmentsFxList<>();
    private static ShipmentViewModel scanViewModel;

    private BooleanProperty busy = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
        scanViewModel = new ShipmentViewModel();
        listAnchorPane.getChildren().clear();
        lastScanedList.getSelectionModel().clearSelection();
        lastScanedList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        scanViewModel.getList().clear();
        imageLoad.visibleProperty().bind(busy);
        contentPane.disableProperty().bind(busy);
        busy.addListener( (obj, oldVal, newVal) -> {
//            System.out.println("0. " + newVal);
            if (newVal){
//                ShipmentDownloadUtil.getInstance();
//                System.out.println("1. " + shipmentDownloadUtil.task);
//                if (shipmentDownloadUtil.task != null && shipmentDownloadUtil..progressProperty() != null) {
//                    System.out.println("1. " + ShipmentDownloadUtil.getTask().progressProperty());
////                if (ShipmentDownloadUtil.getInstance().getService() != null) {
//                    progressBar.progressProperty().bind(ShipmentDownloadUtil.getInstance().getService().progressProperty());
//                }
//                    progressBar.progressProperty().bind(ShipmentDownloadUtil.getInstance().getTask().progressProperty());
//                }
            }else {
//                progressBar.progressProperty().unbind();
            }
        });


        lastScanedList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                newSelection.switchedOnProperty().setValue(true);
                newSelection.selectedNameProperty().setValue(newSelection.nameProperty().getValue());
            }
            if (oldSelection != null) {
                oldSelection.switchedOnProperty().setValue(false);
            }
        });
        if (SessionManager.connectionStatus.getValue()) {
            updateShipments();
        } else {
            info.setText("Brak połączenia z serwerem.");
            info.setFill(Color.RED);
            busy.setValue(false);
        }
    }

    private void updateShipments() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater( ()->{
                            busy.setValue(true);
                            info.setText("Pobieram listę zwrotów.");
                        });

                        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");
                        SettingDao setting = null;
                        if (!settingDao.isPresent()) {
                            setting = new SettingDao(null,"shipment_id","0",new Date());
                        } else{
                            setting = settingDao.get();
                        }
                        setting.setValue("0");
                        SQLiteSettingService.getInstance().save(setting);

                        SessionManager.getInstance()
                                .setShipmentListItemDtoList(
                                        RestService
                                                .getInstance()
                                                .getShipmentsListItems()
                                );
                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                Platform.runLater( ()->{
                    info.setText("");
                    busy.setValue(false);
                });
                try {
                    scanViewModel.init();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                lastScanedList.getItems().clear();
                lastScanedList.getItems().addAll(scanViewModel.getList());

                AnchorPane.setTopAnchor(lastScanedList, 0.0);
                AnchorPane.setLeftAnchor(lastScanedList, 0.0);
                AnchorPane.setRightAnchor(lastScanedList, 0.0);
                AnchorPane.setBottomAnchor(lastScanedList, 0.0);
                listAnchorPane.getChildren().add(lastScanedList);

                //Called when finished without exception
            }
        };
        service.start(); // starts Thread
    }

    public static synchronized ShipmentsListController getInstance() {
        if (instance == null) {
            instance = new ShipmentsListController();
        }
        return instance;
    }

    private static void setInstance(ShipmentsListController instance) {
        ShipmentsListController.instance = instance;
    }

    public void onBackAction() {
        RootController.getInstance().openMain();
    }

    public void onRefreshAction() {
//        busy.setValue(true);
//
//        boolean okClicked = RootController.getInstance().openToast("Odświeżanie","Czy odświeżyć dane?", "#4CAF50");
//            if (okClicked) {
//                updateShipments();
//            }
//
//        busy.setValue(false);
//        shipmentListItemDtoList = RestService.getInstance().getShipmentsListItems();
//        shipmentsList.clear();
//        for (ShipmentDto shipmentDto : shipmentListItemDtoList.getShipmentsDto()) {
//            shipmentsList.add(shipmentDto);
//        }
//
//        shipmentsListView.setItems(shipmentsList);
    }

    public void setInfo(String info) {
        this.info.setText(info);
    }

    public Text getInfo() {
        return info;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void setBusy(boolean busy) {
        this.busy.set(busy);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
