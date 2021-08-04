package pl.estrix.zwrotpaczek.controller.about;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.controller.RootController;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    private static AboutController instance = null;

    @FXML
    private TextField build;

    @FXML
    private TextField version;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);

        build.setText(SessionManager.properties.get("estrix.application.biuld-time"));
        version.setText(SessionManager.properties.get("estrix.application.version_curr"));
    }

    public static synchronized AboutController getInstance() {
        if (instance == null) {
            instance = new AboutController();
        }
        return instance;
    }

    private static void setInstance(AboutController instance) {
        AboutController.instance = instance;
    }

    public void onBackAction() {
        RootController.getInstance().openMain();
    }

}