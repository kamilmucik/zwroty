package pl.estrix.zwrotpaczek.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.estrix.zwrotpaczek.AppMain;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.main.MainController;
import pl.estrix.zwrotpaczek.controller.notification.NotificationController;
import pl.estrix.zwrotpaczek.controller.scan.ScanController;
import pl.estrix.zwrotpaczek.controller.settings.SettingsController;
import pl.estrix.zwrotpaczek.controller.shipments.ShipmentsListController;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dto.ReturnDialogDto;
import pl.estrix.zwrotpaczek.service.SQLiteSettingService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RootController implements Initializable {

    private static final Logger LOG = Logger.getLogger(RootController.class.getName());

    private static RootController instance = null;

    private AppMain appMain;

    @FXML
    private BorderPane rootLayoutPane;


    @FXML
    private AnchorPane apToastBackground;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);


        SessionManager.getInstance().restoreSession();
        SessionManager.getInstance().loadProperties("/filter.properties");

//        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("session_id");
//        if (settingDao.isPresent()) {
//            openScan(null);
//        }else{
            openMain();
//        }



    }

    public static synchronized RootController getInstance() {
        if (instance == null) {
            instance = new RootController();
        }
        return instance;
    }

    private static void setInstance(RootController instance) {
        RootController.instance = instance;
    }

    void setCenter(String fxmlPath, ViewParam param) {
        FXMLLoader loader = new FXMLLoader(AppMain.class.getResource(fxmlPath));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return MainController.getInstance() ;
            } else if (controllerClass == SettingsController.class) {
                return SettingsController.getInstance() ;
            } else if (controllerClass == ShipmentsListController.class) {
                return ShipmentsListController.getInstance() ;
            } else if (controllerClass == ScanController.class) {
                return ScanController.getInstance() ;
            } else if (controllerClass == NotificationController.class) {
                return NotificationController.getInstance() ;
            } else {
                try {
                    return controllerClass.newInstance();
                } catch (Exception exc) {
                    LOG.severe(exc.getMessage());
                    return null;
                }
            }
        });
        try {
            Node node = loader.load();
            if (loader.getController() instanceof Configurable){
                ((Configurable) loader.getController()).setParams(param);
            }
            node.setStyle("/style.css");

            rootLayoutPane.setCenter(node);
        } catch (Exception e) {
//            LOG.severe(e.getMessage());
        }
    }

    public void setAppMain(AppMain appMain) {
        this.appMain = appMain;
    }

    @FXML
    public void openScan(ViewParam param) {
        setCenter("/fxml/Scan.fxml", param);
    }
    @FXML
    public void openNotification(ViewParam param) {
        setCenter("/fxml/Notification.fxml", param);
    }

    @FXML
    public void openShipment(ViewParam param) {
        setCenter("/fxml/Shipment.fxml", param);
    }

    @FXML
    public void openShipmentsList() {
        rootLayoutPane.disableProperty().setValue(true);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(true);
//        if(openPinDialog().get().booleanValue()) {
            setCenter("/fxml/ShipmentsList.fxml", null);
//        }
        MainController.getInstance().getImageLoad().visibleProperty().setValue(false);
        rootLayoutPane.disableProperty().setValue(false);
    }
    @FXML
    public void openRelease() {
        rootLayoutPane.disableProperty().setValue(true);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(true);
        setCenter("/fxml/Release.fxml", null);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(false);
        rootLayoutPane.disableProperty().setValue(false);
    }
    @FXML
    public void openFoto() {
        rootLayoutPane.disableProperty().setValue(true);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(true);
        setCenter("/fxml/Foto.fxml", null);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(false);
        rootLayoutPane.disableProperty().setValue(false);
    }

    @FXML
    public void openSettings() {
        setCenter("/fxml/Settings.fxml", null);
    }

    @FXML
    public void openAbout() {
        setCenter("/fxml/About.fxml", null);
    }

    @FXML
    public void openMain() {
        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");
        SettingDao setting = null;
        if (!settingDao.isPresent()) {
            setting = new SettingDao(null,"shipment_id","0",new Date());
        } else{
            setting = settingDao.get();
        }
        setting.setValue("0");
        SQLiteSettingService.getInstance().save(setting);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        settingDao = SQLiteSettingService.getInstance().getByFieldName("last_updated");
        if (!settingDao.isPresent()) {
            setting = new SettingDao(null,"last_updated","0",new Date());
        } else{
            setting = settingDao.get();
        }
        setting.setValue(simpleDateFormat.format(new Date()));
        SQLiteSettingService.getInstance().save(setting);

        setCenter("/fxml/Main.fxml", null);
        MainController.getInstance().getImageLoad().visibleProperty().setValue(false);
    }

    public boolean openToast(String head,String content,String color) {
        apToastBackground.setVisible(true);
        boolean tmp = appMain.showAboutDialog(head,content, color);
        apToastBackground.setVisible(false);
        return tmp;
//        return appMain.showAboutDialog(head,content, color);
    }

    public ReturnDialogDto<Integer> openScanMultiplerDialog(String name) {
        apToastBackground.setVisible(true);
        ReturnDialogDto<Integer> tmp = appMain.showScanMultiplerDialog(name);
        apToastBackground.setVisible(false);
        return tmp;
    }

    public ReturnDialogDto<Integer> openScanListDialog() {
        apToastBackground.setVisible(true);
        ReturnDialogDto<Integer> tmp = appMain.showScanListDialog();
        apToastBackground.setVisible(false);
        return tmp;
    }
    public ReturnDialogDto<Integer> openShopProductListDialog(ViewParam params) {
//        return appMain.openShopProductListDialog(params);
        apToastBackground.setVisible(true);
        ReturnDialogDto<Integer> tmp = appMain.openShopProductListDialog(params);;
        apToastBackground.setVisible(false);
        return tmp;
    }

    public ReturnDialogDto<Boolean> openPinDialog() {
//        return appMain.showPinDialog();
        apToastBackground.setVisible(true);
        ReturnDialogDto<Boolean> tmp = appMain.showPinDialog();
        apToastBackground.setVisible(false);
        return tmp;
    }
}
