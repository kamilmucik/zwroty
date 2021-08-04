package pl.estrix.zwrotpaczek.controller.dialog.scanlist;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.estrix.zwrotpaczek.dao.model.ListResponseDto;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;
import pl.estrix.zwrotpaczek.dto.ReturnDialogDto;
import pl.estrix.zwrotpaczek.service.SQLiteShipmentProductService;

public class ScanListDialogController {

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private Label header;
    @FXML
    private AnchorPane headPane;
    @FXML
    private TableView<ShipmentProductDao> tableView;
    @FXML
    private TableColumn<ShipmentProductDao, String> nameColumn;
    @FXML
    private TableColumn<ShipmentProductDao, Integer> correctColumn;
    @FXML
    private TableColumn<ShipmentProductDao, Integer> damageColumn;


    private ObservableList<ShipmentProductDao> shipmentData = FXCollections.observableArrayList();

    private IntegerProperty currentPage = new SimpleIntegerProperty(1);
    private IntegerProperty totalPage = new SimpleIntegerProperty(0);

    private BooleanProperty leftButtonProperty = new SimpleBooleanProperty(true);
    private BooleanProperty rightButtonProperty = new SimpleBooleanProperty(true);

    @FXML
    private Hyperlink leftButton;
    @FXML
    private Hyperlink rightButton;

    @FXML
    private void initialize() {
        header.textProperty().bind(new SimpleStringProperty("Strona: ").concat(currentPage.asString()).concat(" z ").concat(totalPage));
        tableView.setItems(shipmentData);
        leftButton.disableProperty().bind(leftButtonProperty);
        rightButton.disableProperty().bind(rightButtonProperty);

        nameColumn
                .setCellValueFactory(new PropertyValueFactory<>(
                        "name"));
        correctColumn
                .setCellValueFactory(new PropertyValueFactory<>(
                        "scanCorrect"));
        damageColumn
                .setCellValueFactory(new PropertyValueFactory<>(
                        "scanError"));


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


        updateList();
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
        int pageLimit = 5;
        PagingCriteria pagingCriteria = new PagingCriteria(0, (currentPage.get()-1) * pageLimit,pageLimit);
        OptionalDao<ListResponseDto<ShipmentProductDao>> result= SQLiteShipmentProductService.getInstance().getList(pagingCriteria);

        shipmentData.clear();
        if (result.isPresent() && currentPage.getValue() <= result.get().getTotalCount()) {
            totalPage.setValue(result.get().getTotalCount());
            shipmentData.addAll(result.get().getData());
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
}
