package pl.estrix.zwrotpaczek.controller.dialog.scanlist.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface ScanListFx {

    LongProperty idProperty();

    StringProperty nameProperty();

    StringProperty eanProperty();

    LongProperty cntAllProperty();

}
