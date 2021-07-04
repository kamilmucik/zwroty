package pl.estrix.zwrotpaczek.controller.dialog.shoplist;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.dialog.shoplist.model.ScanListFx;
import pl.estrix.zwrotpaczek.controller.dialog.shoplist.model.ScanListFxList;
import pl.estrix.zwrotpaczek.controller.dialog.shoplist.model.ScanListFxViewModel;
import pl.estrix.zwrotpaczek.dao.model.*;
import pl.estrix.zwrotpaczek.dto.ReturnDialogDto;
import pl.estrix.zwrotpaczek.service.SQLiteShipmentProductService;
import pl.estrix.zwrotpaczek.service.SQLiteShipmentProductShopService;

import java.util.ArrayList;

public class ShopProductListDialogController implements Configurable {

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private Label header;
    @FXML
    private Label header1;
    @FXML
    private TextArea eanListArea;
    @FXML
    private AnchorPane headPane;
    @FXML
    private AnchorPane listAnchorPane;
//    @FXML
//    private TableView<ShipmentProductShopDao> tableView;
//    @FXML
//    private TableColumn<ShipmentProductShopDao, String> nameColumn;
//    @FXML
//    private TableColumn<ShipmentProductShopDao, Integer> counterColumn;


    private ObservableList<ShipmentProductShopDao> shipmentData = FXCollections.observableArrayList();

    private IntegerProperty currentPage = new SimpleIntegerProperty(1);
    private IntegerProperty totalPage = new SimpleIntegerProperty(0);

    private BooleanProperty leftButtonProperty = new SimpleBooleanProperty(true);
    private BooleanProperty rightButtonProperty = new SimpleBooleanProperty(true);


    private static ScanListFxList<ScanListFx> lastScanedList = new ScanListFxList<>();
    private static ScanListFxViewModel scanViewModel;

    @FXML
    private Hyperlink leftButton;
    @FXML
    private Hyperlink rightButton;

    private Long shopNr = 0L;
    private Long sendNr =0L;

    @FXML
    private void initialize() {
        header.textProperty().bind(new SimpleStringProperty("Strona: ").concat(currentPage.asString()).concat(" z ").concat(totalPage));
//        tableView.setItems(shipmentData);
        leftButton.disableProperty().bind(leftButtonProperty);
        rightButton.disableProperty().bind(rightButtonProperty);


        scanViewModel = new ScanListFxViewModel();
        scanViewModel.getList().clear();

        lastScanedList.getItems().clear();
        lastScanedList.getItems().addAll(scanViewModel.getList());

        AnchorPane.setTopAnchor(lastScanedList, 0.0);
        AnchorPane.setLeftAnchor(lastScanedList, 0.0);
        AnchorPane.setRightAnchor(lastScanedList, 0.0);
        AnchorPane.setBottomAnchor(lastScanedList, 0.0);
        listAnchorPane.getChildren().add(lastScanedList);


        lastScanedList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OptionalDao<ShipmentProductDao> productOpt = SQLiteShipmentProductService.getInstance().getByArtNumber(newSelection.eanProperty().getValue());
                if (productOpt.isPresent()){
                    eanListArea.setText("" + productOpt.get().getEan());
                }
            }
        });

        totalPage.addListener( (observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)){
                rightButtonProperty.setValue(false);
            }
        });
        currentPage.addListener( (observable, oldValue, newValue) -> {
            if (newValue.longValue() <= 1L){
                leftButtonProperty.setValue(true);
            }else {
                leftButtonProperty.setValue(false);
            }
            if (newValue.longValue() > totalPage.getValue()-1){
                rightButtonProperty.setValue(true);
            } else {
                rightButtonProperty.setValue(false);
            }
        });


//        updateList();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public ReturnDialogDto<Integer> isOkClicked() {
        ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
        result.setStatus(0);
        return result;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    private void updateList(){
        int pageLimit = 4;
        PagingCriteria pagingCriteria = new PagingCriteria(0, (currentPage.get()-1) * pageLimit,pageLimit);
        OptionalDao<ListResponseDto<ShipmentProductShopDao>> result= SQLiteShipmentProductShopService.getInstance().getList(pagingCriteria, shopNr, sendNr);


//        System.out.println("isPresent: " + result.isPresent());

        shipmentData.clear();
        if (result.isPresent() && currentPage.getValue() <= result.get().getTotalCount()) {
            totalPage.setValue(result.get().getTotalCount());

            scanViewModel.init(result.get().getData());
            lastScanedList.getItems().clear();
            lastScanedList.getItems().addAll(scanViewModel.getList());
//            result.get().getData().stream().forEach(System.out::println);
//            shipmentData.addAll(result.get().getData());
        }
    }

    public void onDecrementAnction(ActionEvent actionEvent) {
        currentPage.setValue(currentPage.get()-1);
        updateList();
    }

    public void onIncrementAnction(ActionEvent actionEvent) {
        currentPage.setValue(currentPage.get()+1);
        updateList();
    }

    @Override
    public void setParams(ViewParam params) {
//        System.out.println("ShopProductListDialogController.ViewParam");
//        System.out.println("getReturnNr: " + params.getReturnNr());
//        System.out.println("getShopNr: " + params.getShopNr());
//        System.out.println("getSendNr: " + params.getSendNr());
        shopNr = params.getShopNr();
        sendNr = params.getSendNr();
        header1.setText("Sklep: " + shopNr);
        updateList();
    }
}
