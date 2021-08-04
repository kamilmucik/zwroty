package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToogleSwitchTest extends Parent {

    private BooleanProperty switchedOn = new SimpleBooleanProperty(false);

    private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.15));
    private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.15));
    private ParallelTransition animation = new ParallelTransition(translateAnimation,fillAnimation);

    public BooleanProperty switchedOnProperty() {
        return switchedOn;
    }


    public ToogleSwitchTest(){
        getStyleClass().add("toogleSwitchTest");
        Rectangle background =new Rectangle(80, 60);
        background.setFill(Color.WHITE);
        background.setStroke(Color.WHITE);

        Polygon trigger = new Polygon();
        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.WHITE);
        trigger.getPoints().addAll(new Double[]{
                5.0, 5.0,
                25.0, 30.0,
                5.0, 55.0 });

        translateAnimation.setNode(trigger);
        fillAnimation.setShape(background);

        getChildren().addAll(background,trigger);

        switchedOn.addListener((obs, oldState, newState) -> {
            boolean isOn = newState.booleanValue();
            translateAnimation.setToX(isOn ? (80 - 50) : 0);
            fillAnimation.setFromValue(isOn ? Color.WHITE : Color.GREEN);
            fillAnimation.setToValue(isOn ? Color.GREEN : Color.WHITE);

            animation.play();
        });

    }
}
