package pl.estrix.zwrotpaczek.controller.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import pl.estrix.zwrotpaczek.PlatformService;
import pl.estrix.zwrotpaczek.controller.RootController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {

    private static MainController instance = null;

    @FXML
    private ImageView imageLoad;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
    }

    public static synchronized MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    private static void setInstance(MainController instance) {
        MainController.instance = instance;
    }

    public void openShipmentsList() {
        RootController.getInstance().openShipmentsList();
    }
    public void openRelease() {
        RootController.getInstance().openRelease();
    }
    public void openFoto() {
        RootController.getInstance().openFoto();
    }

    public void openSettings() {
       RootController.getInstance().openSettings();
    }

    public void openAbout() {
        RootController.getInstance().openAbout();
    }

    public void onCloseAction() {
        PlatformService.getInstance().exit();
    }

    public ImageView getImageLoad() {
        return imageLoad;
    }

    public void setImageLoad(ImageView imageLoad) {
        this.imageLoad = imageLoad;
    }
}