package pl.estrix.zwrotpaczek.controller.scan.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public interface ScanProductFx {

    LongProperty idProperty();

    StringProperty nameProperty();

    StringProperty eanProperty();

    StringProperty storeProperty();

    LongProperty cntAllProperty();

    LongProperty cntCorrectProperty();

    LongProperty cntErrorProperty();

    ObjectProperty<Image> imageProperty();
}
