package pl.estrix.zwrotpaczek.controller.notification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import pl.estrix.zwrotpaczek.Configurable;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable,Configurable {

    private static NotificationController instance = null;
    @FXML
    public AnchorPane contentPane;

    @FXML
    private TextArea notificationArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);

        //System.out.println("NotificationController");
    }


    public static synchronized NotificationController getInstance() {
        if (instance == null) {
            instance = new NotificationController();
        }
        return instance;
    }

    private static void setInstance(NotificationController instance) {
        NotificationController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
        notificationArea.setText(params.getMessage());
        //System.out.println("NotificationController.params: " + params.getMessage());
    }

    @FXML
    public void onReturn(ActionEvent actionEvent) {
        RootController.getInstance().openMain();
    }
}
