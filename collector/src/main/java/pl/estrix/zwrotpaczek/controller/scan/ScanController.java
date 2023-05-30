package pl.estrix.zwrotpaczek.controller.scan;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.dao.model.*;
import pl.estrix.zwrotpaczek.dto.*;
import pl.estrix.zwrotpaczek.service.Ean13;
import pl.estrix.zwrotpaczek.service.RestService;
import pl.estrix.zwrotpaczek.service.SQLiteSettingService;
import pl.estrix.zwrotpaczek.service.SQLiteShipmentProductService;

import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScanController implements Initializable,Configurable {

    private static ScanController instance = null;

    private StringBuilder eanCode = new StringBuilder();

    private String retNumber = "000000";

    private Integer multipler;
    private Integer palletCounter=1;

    @FXML
    public ComboBox stateBox;

    @FXML
    public Button palletCounterButton;

    @FXML
    private Button multiplerButton;

    @FXML
    private Label label1;
    @FXML
    private Label text01;
    @FXML
    private Label text02;
    @FXML
    private Label text03;
    @FXML
    private Label text04;
    @FXML
    private Label text05;
    @FXML
    private Label text06;
    @FXML
    private Label text07;

    @FXML
    private Label selectedName;
    @FXML
    private Label selectedLogLabel;

    @FXML
    private Label selectedEAN;

    @FXML
    private Label selectedCntBroken;

    @FXML
    private Label selectedCntCorrect;
    @FXML
    private Label selectedUtylizationLabel;
    @FXML
    private Label selectedCntLabel;
//    @FXML
//    private Label selectedProductLabel;

    @FXML
    private Label selectedCntAll;

    @FXML
    private Label selectedStore;

    @FXML
    private Label selectedArtNumber;

    @FXML
    private VBox mainVBox;

    @FXML
    private Hyperlink closeLink;
    @FXML
    private Hyperlink scanedList;

//    @FXML
//    private Button triStateButton;
    @FXML
    private Button triStatePalletButton;

    @FXML
    private ToggleButton storageToggle;
    @FXML
    private ToggleButton printerToggle;

    @FXML
    private ImageView imageLoad;
    @FXML
    private Parent contentPane;

    @FXML
    private TextField eanLabel;

    @FXML
    private Text info;
    @FXML
    private Label headLabel;
    @FXML
    private Label debugLogInfo;

    @FXML
    private Ellipse eanFieldSematphor;

    @FXML
    private Hyperlink eanFieldSematphorLink;

    private BooleanProperty print = new SimpleBooleanProperty(false);
    private BooleanProperty busy = new SimpleBooleanProperty();
    private BooleanProperty canIScan = new SimpleBooleanProperty();
    private BooleanProperty storageSave = new SimpleBooleanProperty(false);

    private long keyPressedTime = 0L;
    private long keyReleaseTime = 0L;
    private boolean canIScann = true;
    private Long shopNr;

    private int optionPalletButtonIndex = 0;
//    private int optionButtonIndex = 0;
//    private String[] optionName = {"Dobre","Uszkodzone","Z ceną"};

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Dobre",
                    "Uszkodzone",
                    "Z ceną",
                    "Utylizacja"
            );

    private String[] optionPalletName = {"C","M"};

    private String eanZeros;

    private ShipmentProductDto selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
        multipler = 1;

        stateBox.setItems(options);
        stateBox.setValue("Dobre");

        info.visibleProperty().bind(SessionManager.connectionStatus.not());
        info.setText("Brak połączenia z serwerem.");
        info.setFill(Color.RED);

        imageLoad.visibleProperty().bind(canIScan);

        label1.disableProperty().bind(busy);
        text01.disableProperty().bind(busy);
        text02.disableProperty().bind(busy);
        text03.disableProperty().bind(busy);
        text04.disableProperty().bind(busy);
        text05.disableProperty().bind(busy);
        text06.disableProperty().bind(busy);
        text07.disableProperty().bind(busy);

//        triStateButton.disableProperty().bind(busy);
        triStatePalletButton.disableProperty().bind(busy);
        storageToggle.disableProperty().bind(busy);
        scanedList.disableProperty().bind(print);

        selectedName.disableProperty().bind(busy);
        selectedLogLabel.disableProperty().bind(busy);
        selectedEAN.disableProperty().bind(busy);
        selectedCntBroken.disableProperty().bind(busy);
        selectedCntCorrect.disableProperty().bind(busy);
        selectedCntLabel.disableProperty().bind(busy);
        selectedCntAll.disableProperty().bind(busy);
        selectedStore.disableProperty().bind(busy);
        selectedArtNumber.disableProperty().bind(busy);
        selectedUtylizationLabel.disableProperty().bind(busy);

        Platform.runLater(() -> eanLabel.requestFocus());

        triStatePalletButton.setText(optionPalletName[0]);
        triStatePalletButton.pressedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                optionPalletButtonIndex++;
                if (optionPalletButtonIndex > 1){
                    optionPalletButtonIndex = 0;
                }
                int index = optionPalletButtonIndex;
                triStatePalletButton.setText(optionPalletName[index]);
                triStatePalletButton.getStyleClass().clear();
                triStatePalletButton.getStyleClass().add("button");
                triStatePalletButton.getStyleClass().add("custom-button");
                triStatePalletButton.getStyleClass().add("custom-button-damage"+index);
            }
        });

        stateBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> eanLabel.requestFocus());
        });
        printerToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (printerToggle.isSelected()){
                busy.setValue(true);
                print.setValue(true);
                Platform.runLater(() -> eanLabel.requestFocus());
            }else {
                busy.setValue(false);
                print.setValue(false);
            }
        });

        storageToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (storageToggle.isSelected()){
                Platform.runLater(() -> eanLabel.requestFocus());
            }
        });

        printerToggle.focusedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> eanLabel.requestFocus()));
//        triStateButton.focusedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> eanLabel.requestFocus()));
        triStatePalletButton.focusedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> eanLabel.requestFocus()));
        storageToggle.focusedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> eanLabel.requestFocus()));
        eanLabel.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                eanFieldSematphorLink.setVisible(false);
            } else {
                eanFieldSematphorLink.setVisible(true);
            }
        });

        eanLabel.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)
                    ) {
                eanCode.delete(0, eanCode.length());
                eanZeros = null;
                Platform.runLater(() -> {
                    selectedName.setText("");
                    selectedCntBroken.setText("");
                    selectedLogLabel.setText("");
                    selectedEAN.setText("");
                    selectedCntCorrect.setText("");
                    selectedCntLabel.setText("");
                    selectedCntAll.setText("");
                    selectedStore.setText("");
                    selectedArtNumber.setText("");
                    debugLogInfo.setText("");
                    eanLabel.clear();
                });
                return;
            }

            debugLogInfo.setText("c: " + event.getCode().getName() + "_"+event.getCode());
            if (event.getCode().equals(KeyCode.ENTER)
                    || event.getCode().equals(KeyCode.UNDEFINED)
                    ) {
                eanCode.delete(0, eanCode.length());
                eanZeros = null;
                eanCode.append(eanLabel.getText());

                eanZeros = leftPadZeros(eanCode.toString().trim(), 13);
                selectedEAN.setText(eanZeros);
                search(eanZeros);
                Platform.runLater(() -> {
                    eanLabel.clear();
                    eanLabel.requestFocus();});
                return;
            }
        });

        headLabel.textProperty().addListener( ((observable, oldValue, newValue) -> {

            Integer counter = 0;
            try{
                counter = Integer.parseInt(getPalletCounter(newValue));
            }catch (NumberFormatException e){

            }

            palletCounter = counter;
            palletCounterButton.setText(""+counter);
        }));

   }

    private void search(String code) {
        busy.setValue(true);
        canIScan.setValue(true);
        if (Ean13.getInstance().validate(code) && (!code.isEmpty() && !"0000000000000".equals(code)) && !retNumber.isEmpty()){
            ShipmentProductDto shipmentProductDto = RestService.getInstance().findProductByEAN(retNumber,code);
            if (shipmentProductDto!=null && shipmentProductDto.getArtNumber()!= null ) {
                eanLabel.clear();
                selectedLogLabel.setText(getStringBuilderLog(shipmentProductDto).toString());

                selectedName.setText(shipmentProductDto.getName());
                selectedCntBroken.setText(shipmentProductDto.getScanError().toString());
                selectedCntCorrect.setText(shipmentProductDto.getScanCorrect().toString());
                selectedCntLabel.setText(shipmentProductDto.getScanLabel().toString());
                selectedUtylizationLabel.setText(shipmentProductDto.getScanUtilization().toString());
                selectedCntAll.setText(shipmentProductDto.getCounter().toString() + "(" +(shipmentProductDto.getScanError()+shipmentProductDto.getScanCorrect())+ ")");
                selectedArtNumber.setText(shipmentProductDto.getArtNumber().toString());
                try {
                    if (printerToggle.isSelected()) {
                        PrintLabelDto dto = new PrintLabelDto();
                        dto.setArtNumber(shipmentProductDto.getArtNumber());
                        dto.setReturnNumber(shipmentProductDto.getArtReturn());
                        dto.setCounter(multipler.longValue());
                        dto.setAuthor("");
                        dto.setPalletCounter(palletCounter);
                        dto.setPalletOption(triStatePalletButton.getText());
                        if (SessionManager.customsProperties.containsKey("operatorName")){
                            dto.setAuthor(SessionManager.customsProperties.get("operatorName"));
                        }
                        PrintLabelDto printLabelDto = RestService.getInstance().printLabel(dto);
//                        if (printLabelDto.getReturnCode() == 200 && palletCounter>0) {
//                            savePalletCounter(, shipmentProductDto.getArtReturn());
//                        }
                    }
                    if (!storageToggle.selectedProperty().getValue()) {
                        multipler = 0;
                    }else{
                        shipmentProductDto.setScanCorrect(0L);
                        shipmentProductDto.setScanError(0L);
                        shipmentProductDto.setScanLabel(0L);
                        int optionButtonIndex = stateBox.getSelectionModel().getSelectedIndex();

                        if (optionButtonIndex == 0){
                            shipmentProductDto.setScanCorrect((long)multipler);
                        }else if (optionButtonIndex == 1){
                            shipmentProductDto.setScanError((long)multipler);
                        }else if (optionButtonIndex == 2){
                            shipmentProductDto.setScanLabel((long)multipler);
                        }else if (optionButtonIndex == 3){
                            shipmentProductDto.setScanUtilization((long)multipler);
                        }

//                        Platform.runLater(() -> {
//                            selectedProductLabel.setText(optionName[optionButtonIndex]);
//                        });
                    }
                    if (shipmentProductDto != null && multipler > 0 ) {
                        shipmentProductDto = RestService.getInstance().updateProduct(
                                retNumber,
                                code,
                                shipmentProductDto.getScanCorrect(),
                                shipmentProductDto.getScanError(),
                                shipmentProductDto.getScanLabel(),
                                shipmentProductDto.getScanUtilization()
                        );

                        if (shipmentProductDto.getReturnCode() == 200) {

                            eanLabel.clear();
                            selectedName.setText(shipmentProductDto.getName());
                            selectedCntBroken.setText(shipmentProductDto.getScanError().toString());
                            selectedCntCorrect.setText(shipmentProductDto.getScanCorrect().toString());
                            selectedCntLabel.setText(shipmentProductDto.getScanLabel().toString());
                            selectedUtylizationLabel.setText(shipmentProductDto.getScanUtilization().toString());
                            selectedCntAll.setText(shipmentProductDto.getCounter().toString() + "(" + (shipmentProductDto.getScanError() + shipmentProductDto.getScanCorrect()) + ")");
                            selectedArtNumber.setText(shipmentProductDto.getArtNumber().toString());
                            selectedLogLabel.setText(getStringBuilderLog(shipmentProductDto).toString());
                        }

                    }else {
                        if (!print.get()) {
                            RootController.getInstance().openToast(
                                    "Blad",
                                    "Popraw ilość produktów.\r\nWybrano: " + multipler + "\r\ndla " + shipmentProductDto.getName(),
                                    "#4CAF50");
                        }
                    }

                    multipler = 0;
                    multiplerButton.setText("x0");
                }catch (NumberFormatException e){
//                    selectedStore.setText("Brak numeru sklepu. Zeskanuj pudełko!");
                    multipler = 0;
                    multiplerButton.setText("x0");
                }
            } else {
                selectedName.setText("Brak produktu w bazie.");
                selectedCntBroken.setText("");
                selectedCntCorrect.setText("");
                selectedUtylizationLabel.setText("");
                selectedCntAll.setText("");
                selectedStore.setText("");
                selectedArtNumber.setText("");
                selectedLogLabel.setText("");
            }
            eanCode.delete(0, eanCode.length());
        } else {
            selectedName.setText("Brak produktu w bazie.");
            selectedCntBroken.setText("");
            selectedCntCorrect.setText("");
            selectedCntLabel.setText("");
            selectedUtylizationLabel.setText("");
            selectedCntAll.setText("");
            selectedLogLabel.setText("");
            selectedStore.setText("");
            selectedArtNumber.setText("");
        }

        busy.setValue(false);
        canIScan.setValue(false);

        Platform.runLater(() -> {
            eanLabel.clear();
            eanLabel.requestFocus();
        });
    }

    private StringBuilder getStringBuilderLog(ShipmentProductDto shipmentProductDto) {
        StringBuilder log = new StringBuilder();

        if (shipmentProductDto != null && shipmentProductDto.getScanLog()!= null) {
            String[] logs = shipmentProductDto.getScanLog().split("\\+");

            for (int i = logs.length - 1; i >= 0; i--) {
                if (log.length() > 0) {
                    log.append("+");
                }
                log.append(logs[i]);
            }
        }
        return log;
    }

    public void scanCode(long lastTypeTime) {
       long delta = lastTypeTime - keyReleaseTime;
       long deltaPressd = lastTypeTime - keyPressedTime;
        if (keyPressedTime > 0 && keyReleaseTime > 0
            && delta > 500
                && deltaPressd > 500
            ) {
            Platform.runLater(() ->  {
                canIScan.setValue(false);
                selectedEAN.setText(eanZeros);
                search(eanZeros);
            });

            eanCode.delete(0, eanCode.length());
            keyPressedTime = 0;
            keyReleaseTime = 0;
        }
   }

   private void savePalletCounter(String inputValue, String returnNumber){
       String returnPalletName = "pallet_"+returnNumber.replace(" ", "_").replace(":", "_");
       OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName(returnPalletName);
       SettingDao setting = null;
       if (!settingDao.isPresent()) {
           setting = new SettingDao(null,returnPalletName,inputValue,new Date());
       } else{
           setting = settingDao.get();
       }
       setting.setUpdate(new Date());
       setting.setValue(inputValue);
       OptionalDao<Integer> status = SQLiteSettingService.getInstance().save(setting);
       if ( status.getStatus() == 0){
           String id = "" + setting.getId();
           Platform.runLater(() -> palletCounterButton.setText(inputValue));
       }else {
           Platform.runLater(() -> eanLabel.setText(status.getMessage()));

           RootController.getInstance().openToast("Blad: " + status.getStatus(),status.getMessage(),"#4CAF50");
       }
   }

   private String getPalletCounter(String returnNumber){
       SettingDao setting = null;
       String returnPalletName = "pallet_"+returnNumber.replace(" ", "_").replace(":", "_");
       OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName(returnPalletName);

       if (!settingDao.isPresent()) {
           return "1";
       } else{
           setting = settingDao.get();
//           System.out.println("getPalletCounter: " + returnPalletName + " : " + setting.getValue());
            String value = setting.getValue();
//           Platform.runLater(() -> eanLabel.setText("g: "+returnPalletName+":"+value));
           Platform.runLater(() -> palletCounterButton.setText(value));
           return value;
       }
   }


    public static synchronized ScanController getInstance() {
        if (instance == null) {
            instance = new ScanController();
        }
        return instance;
    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }

    private static void setInstance(ScanController instance) {
        ScanController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
        retNumber = params.getMessage();
        headLabel.setText("Zwrot: " + retNumber);

    }

    public void onBackAction() {
        RootController.getInstance().openMain();
    }

    @FXML
    public void onFieldFocusAction(ActionEvent actionEvent) {
        Platform.runLater(() -> eanLabel.requestFocus());
    }

    @FXML
    public void onSaveItemAction(ActionEvent actionEvent) {
        busy.setValue(true);
        canIScan.setValue(true);
            busy.setValue(false);
            canIScan.setValue(false);
            RootController.getInstance().openMain();
//        } else {
//            busy.setValue(false);
//            canIScan.setValue(false);
//            Platform.runLater(() -> eanLabel.requestFocus());
//        }
    }

    @FXML
    public void onBackItemAction(ActionEvent actionEvent) {
//        test.setText("onBackItemAction");
    }

    @FXML
    public void onMultiplerWindowAction(ActionEvent actionEvent) {
        ReturnDialogDto<Integer> okClicked = RootController.getInstance().openScanMultiplerDialog(null);
        if (okClicked.getStatus() == 1) {
            multipler = okClicked.get();
            multiplerButton.setText("x"+okClicked.get());
        }
        Platform.runLater(() -> eanLabel.requestFocus());
    }
    @FXML
    public void onMultiplerPalletWindowAction(ActionEvent actionEvent) {
        ReturnDialogDto<Integer> okClicked = RootController.getInstance().openScanMultiplerDialog("Numer Palety");
        if (okClicked.getStatus() == 1) {
            palletCounter = okClicked.get();
            palletCounterButton.setText(""+palletCounter);

            savePalletCounter(palletCounter.toString(), headLabel.getText());
        }
        Platform.runLater(() -> eanLabel.requestFocus());
    }

    @FXML
    public void onScanedListWindowAction(ActionEvent actionEvent) {
        busy.setValue(true);
        canIScan.setValue(true);
        ReturnDialogDto<Integer> okClicked = RootController.getInstance().openScanListDialog();
        if (okClicked.getStatus() == 0) {
        }
        Platform.runLater(() -> {
            eanLabel.requestFocus();
        });
        busy.setValue(false);
        canIScan.setValue(false);

    }

    @FXML
    public void onSaveAction(ActionEvent actionEvent) {
        boolean saved = false;
        boolean isConnected;
    }
}
