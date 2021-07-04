package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public interface ShipmentsFx {

    LongProperty shipmentIdProperty();

    StringProperty nameProperty();

    StringProperty selectedNameProperty();

    LongProperty productCntrProperty();

    BooleanProperty switchedOnProperty();

    ObjectProperty<Image> imageProperty();

}
