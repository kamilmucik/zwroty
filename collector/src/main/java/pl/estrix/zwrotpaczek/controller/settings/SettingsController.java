package pl.estrix.zwrotpaczek.controller.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.service.RestService;
import pl.estrix.zwrotpaczek.service.SQLiteSettingService;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private TextField collectorId;
    @FXML
    private TextField operatorName;
    @FXML
    private Text info;

    private static SettingsController instance = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);

        info.visibleProperty().bind(SessionManager.connectionStatus.not());
        info.setText("Brak połączenia z serwerem.");
        info.setFill(Color.RED);

        if (SessionManager.customsProperties.containsKey("serverAddress")){
            serverAddress.setText(SessionManager.customsProperties.get("serverAddress"));
        }
        if (SessionManager.customsProperties.containsKey("serverPort")){
            serverPort.setText(SessionManager.customsProperties.get("serverPort"));
        }
        if (SessionManager.customsProperties.containsKey("collectorId")){
            collectorId.setText(SessionManager.customsProperties.get("collectorId"));
        }
        if (SessionManager.customsProperties.containsKey("operatorName")){
            operatorName.setText(SessionManager.customsProperties.get("operatorName"));
        }
    }

    public static synchronized SettingsController getInstance() {
        if (instance == null) {
            instance = new SettingsController();
        }
        return instance;
    }

    private static void setInstance(SettingsController instance) {
        SettingsController.instance = instance;
    }

    public void onBackAction() {
        RootController.getInstance().openMain();
    }

    public void onSaveAction() {
//        RootController.getInstance().openNotification(new ViewParam("downloadShipment: 1" ));
        boolean okClicked = RootController.getInstance().openToast("Zapis","Czy zapisać zmiany w ustawieniach?", "#4CAF50");
        if (okClicked) {
            SessionManager.customsProperties.put("serverAddress",serverAddress.getText());
            SessionManager.customsProperties.put("serverPort",serverPort.getText());
            SessionManager.customsProperties.put("collectorId",collectorId.getText());
            SessionManager.customsProperties.put("operatorName",operatorName.getText());
            SessionManager.getInstance().saveSession();

            String session = RestService.getInstance()
                    .getSession(collectorId.getText())
                    .getCollector()
                    .getSessionId();
            OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("session_id");
            if (settingDao.isPresent()) {
                settingDao.get().setValue(session);
                SQLiteSettingService.getInstance().save(settingDao.get());
            }

        }
//        RootController.getInstance().openMain();
    }
}