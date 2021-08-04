package pl.estrix.zwrotpaczek;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;


//https://bitbucket.org/javafxports/samples/src/0baab224193a/HelloPreloader/?at=default
public class MyPreloader extends Preloader {
    private Stage preloaderStage;
    private boolean noLoadingProgress = true;
    private ProgressBar progressBar;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        progressBar = new ProgressBar(-1.0);

        VBox loading = new VBox(20);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);

//        ImageView iv = new ImageView(new Image("/images/chargement.gif"));
//        loading.getChildren().add(iv);
        loading.getChildren().add(progressBar);
        loading.getChildren().add(new Label("Proszę czekać..."));

        BorderPane root = new BorderPane(loading);
        Scene scene;

        if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        } else {
            scene = new Scene(root,320,400);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification info) {
        if (info.getProgress() != 1.0 || !noLoadingProgress) {
            progressBar.setProgress(info.getProgress() / 2.0);
            if (info.getProgress() > 0.0) {
                noLoadingProgress = false;
            }
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof StateChangeNotification) {
            preloaderStage.hide();
        } else if (info instanceof ProgressNotification) {
            ProgressNotification progressInfo = (ProgressNotification) info;
            double progress = progressInfo.getProgress();

            if (progress == 1.00) {
                preloaderStage.hide();
            }

            if (!noLoadingProgress) {
                progress = 0.5 + progress / 2.0;
            }
            progressBar.setProgress(progress);
        }
    }

}