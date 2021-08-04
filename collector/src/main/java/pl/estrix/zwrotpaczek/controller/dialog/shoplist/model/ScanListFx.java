package pl.estrix.zwrotpaczek.controller.dialog.shoplist.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface ScanListFx {

    LongProperty idProperty();

    StringProperty nameProperty();

    StringProperty eanProperty();

    LongProperty cntAllProperty();

}
