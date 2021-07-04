package pl.estrix.zwrotpaczek.controller.release;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.dto.ReleaseArticleDto;
import pl.estrix.zwrotpaczek.service.RestService;

import java.net.URL;
import java.util.ResourceBundle;

public class ReleaseController implements Initializable, Configurable {


    private static ReleaseController instance = null;


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


        Platform.runLater(() -> eanLabel.requestFocus());

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
                    eanLabel.clear();
                });
                return;
            }
            if (event.getCode().equals(KeyCode.ENTER)
                    || event.getCode().equals(KeyCode.UNDEFINED)
            ) {
                eanCode.delete(0, eanCode.length());
                eanZeros = null;
                eanCode.append(eanLabel.getText());

                eanZeros = leftPadZeros(eanCode.toString().trim(), 27);
                selectedEAN.setText(eanZeros);
                search(eanZeros);

                Platform.runLater(() -> {
                    eanLabel.clear();
                    eanLabel.requestFocus();});
                return;
            }
        });

    }

    private void search(String code) {
        busy.setValue(true);
        canIScan.setValue(true);
        busy.setValue(false);
        canIScan.setValue(false);

        //send
        ReleaseArticleDto releaseArticleDto = RestService.getInstance().sendRelease(code);
        if (releaseArticleDto != null && releaseArticleDto.getReturnCode() == 200) {
            selectedEAN.setText("" + code);
        } else {
            selectedEAN.setText("Błąd dodawania: " + code);
        }
        Platform.runLater(() -> {
            eanLabel.clear();
            eanLabel.requestFocus();
        });
    }

    public static synchronized ReleaseController getInstance() {
        if (instance == null) {
            instance = new ReleaseController();
        }
        return instance;
    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }

    private static void setInstance(ReleaseController instance) {
        ReleaseController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
        headLabel.setText("Wysyłka: ");
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
}
