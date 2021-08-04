package pl.estrix.zwrotpaczek.controller.foto;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;

import java.net.URL;
import java.util.ResourceBundle;

public class FotoController implements Initializable, Configurable {


    private static FotoController instance = null;
    @FXML
    public ImageView imageView;


    private BooleanProperty busy = new SimpleBooleanProperty();
    private BooleanProperty canIScan = new SimpleBooleanProperty();

    @FXML
    private Text info;
    @FXML
    private Label headLabel;

    @FXML
    private TextField eanLabel;

    @FXML
    private Label selectedEAN;

    private String eanZeros;

    private StringBuilder eanCode = new StringBuilder();

    @FXML
    private Hyperlink eanFieldSematphorLink;

    @FXML
    private ImageView imageLoad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);

        info.visibleProperty().bind(SessionManager.connectionStatus.not());
        info.setText("Brak połączenia z serwerem.");
        info.setFill(Color.RED);

        imageLoad.visibleProperty().bind(canIScan);
    }

    public static synchronized FotoController getInstance() {
        if (instance == null) {
            instance = new FotoController();
        }
        return instance;
    }

    private static void setInstance(FotoController instance) {
        FotoController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
        headLabel.setText("Zdjęcia");
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
    }

    @FXML
    public void onTakePhoto(ActionEvent event) {

    }
}
