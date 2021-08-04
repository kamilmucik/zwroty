package pl.estrix.zwrotpaczek;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.ProgressNotification;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import pl.estrix.zwrotpaczek.controller.*;
import pl.estrix.zwrotpaczek.controller.dialog.scanlist.ScanListDialogController;
import pl.estrix.zwrotpaczek.controller.dialog.shoplist.ShopProductListDialogController;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dto.ReturnDialogDto;
import pl.estrix.zwrotpaczek.service.RestService;
import pl.estrix.zwrotpaczek.service.SQLiteService;
import pl.estrix.zwrotpaczek.service.SQLiteSettingService;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class AppMain extends Application {

    private Stage primaryStage;

    private Scene scene;

    private Image icon;

    private final BooleanProperty ready = new SimpleBooleanProperty(false);

    public void initialize() throws Exception {
        Platform.runLater(() -> {
            notifyPreloader(new ProgressNotification(-1.0));
        });
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            SQLiteService.getInstance().createTable();



//                            String session = null;
//                            String collectorId = null;
//                            OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("session_id");
//                            SettingDao setting = null;
//                            if (!settingDao.isPresent()) {
//                                if (SessionManager.customsProperties.containsKey("collectorId")){
//                                    collectorId = SessionManager.customsProperties.get("collectorId");
//                                } else {
//                                    collectorId = "0";
//                                }
//
//                                session = RestService.getInstance()
//                                        .getSession(collectorId)
//                                        .getCollector()
//                                        .getSessionId();
//                                setting = new SettingDao(null,"session_id",session,new Date());
//                                int id = SQLiteSettingService.getInstance().save(setting);
//                                settingDao = SQLiteSettingService.getInstance().getById(id);
//                            }
//                            SessionManager.getInstance().setKeyToMap("sessionId", settingDao.get().getValue());
//
//
//                            settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");
//                            if (!settingDao.isPresent()) {
//                                setting = new SettingDao(null,"shipment_id","0",new Date());
//                            } else{
//                                setting = settingDao.get();
//                            }
//                            setting.setValue("0");
//                            SQLiteSettingService.getInstance().save(setting);

                            notifyPreloader(new ProgressNotification(.50));
                        });
                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                ready.set(Boolean.TRUE);
                notifyPreloader(new ProgressNotification(1.00));
            }
        };
        service.start(); // starts Thread
    }

    @Override
    public void init() {


//        System.setOut(new PrintStream(new DevNull()));
    }

    private class DevNull extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialize();
        this.primaryStage = primaryStage;

        try {
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/RootLayout.fxml"));
            Parent rootPane = loader.load();

            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(rootPane, visualBounds.getWidth(), visualBounds.getHeight());
            } else {
                scene = new Scene(rootPane, 393.0,370.0);
                icon = new Image(AppMain.class.getResourceAsStream("/images/ic_launcher.png"));
                this.primaryStage.getIcons().add(icon);
            }
            scene.getStylesheets().add("/style.css");
            primaryStage.setScene(scene);

            SessionManager.getInstance().setScreenResolution("" + scene.getWidth()+"x"+ scene.getHeight());

            PlatformService.getInstance();

            RootController controller = loader.getController();
            controller.setAppMain(this);

            ready.addListener((observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue)) {
                    Platform.runLater(() -> primaryStage.show());
                }
            });
            primaryStage.setOnCloseRequest(we -> PlatformService.getInstance().exit());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAboutDialog(String head,String content,String color) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/Dialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(head);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setY(primaryStage.getY() + 60);
//            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );
            dialogStage.setX( primaryStage.getX()  );
            Scene scene = null;

            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
            } else {
                scene = new Scene(page, 393.0,page.getPrefHeight());
            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            DialogController controller = loader.getController();
            controller.setHeadPaneColor("-fx-background-color: " + color);
            controller.setHeader(head);
            controller.setContent(content);
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
            return false;
        }
    }

    public ReturnDialogDto<Integer> showScanMultiplerDialog(String name) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/ScanMultiplerDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setY(primaryStage.getY() + 60);
//            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );
            dialogStage.setX( primaryStage.getX()  );

            Scene scene = null;

//            scene = new Scene(page, page.getPrefWidth(),page.getPrefHeight());
            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
            } else {
                scene = new Scene(page, 393.0,page.getPrefHeight());
            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            ScanMultiperDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, name);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
            result.set(-1);
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public ReturnDialogDto<Integer> showScanListDialog() {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/ScanListDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setY(primaryStage.getY() + 60);
//            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );
            dialogStage.setX( primaryStage.getX()  );

            Scene scene = null;

            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
            } else {
                scene = new Scene(page, 393.0,page.getPrefHeight());
            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            ScanListDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
            result.set(-1);
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public ReturnDialogDto<Integer> openShopProductListDialog(ViewParam param) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/ShopProductListDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setY(primaryStage.getY() + 60);
//            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );
            dialogStage.setX( primaryStage.getX()  );

            Scene scene = null;

            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
            } else {
                scene = new Scene(page, 393.0,page.getPrefHeight());
            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            ShopProductListDialogController controller = loader.getController();
            if (loader.getController() instanceof Configurable){
                ((Configurable) loader.getController()).setParams(param);
            }
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
            result.set(-1);
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public ReturnDialogDto<Boolean> showPinDialog() {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("/fxml/PinDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setY(primaryStage.getY() + 60);
            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );

            Scene scene = null;

            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
            } else {
                scene = new Scene(page, 393.0,page.getPrefHeight());
            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            PinDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            ReturnDialogDto<Boolean> result = new ReturnDialogDto<>();
            result.set(false);
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}