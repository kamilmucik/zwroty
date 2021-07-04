package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.estrix.zwrotpaczek.ui.CssHelper;
import pl.estrix.zwrotpaczek.ui.DefaultPropertyBasedCssMetaData;
import pl.estrix.zwrotpaczek.ui.StructuredListCell;

import java.util.List;

public class ShipmentsFxListCell<T> extends StructuredListCell<T> {

    private Label nameLabel;
    private Label productCntrLabel;
    private Label descriptionLabel;

    private LongProperty shipmentId;
    private StringProperty name;
    private LongProperty productCntr;

    private StyleableObjectProperty<Boolean> showDescription;
    private StyleableObjectProperty<Number> textSpacing;

    private HBox columns;
    private VBox centerBox;
    private VBox rightBox;

    public ShipmentsFxListCell() {
        getStyleClass().add("media-list-cell");
        name = new SimpleStringProperty();
        productCntr = new SimpleLongProperty();

        columns = new HBox();

        textSpacing = CssHelper.createProperty(ShipmentsFxListCell.StyleableProperties.TEXT_SPACING, this);
        showDescription = CssHelper.createProperty(ShipmentsFxListCell.StyleableProperties.SHOW_DESCRIPTION, this);

        centerBox = new VBox();
        centerBox.spacingProperty().bind(textSpacing);
        centerBox.setFillWidth(true);
        centerBox.getStyleClass().add("scan-box-center");

        nameLabel = new Label();
        nameLabel.getStyleClass().add("media-cell-name");
        nameLabel.textProperty().bind(name);

        centerBox.getChildren().add(nameLabel);

        productCntrLabel = new Label();
        productCntrLabel.getStyleClass().add("media-cell-counter");
        productCntrLabel.textProperty().bind(productCntr.asString());

        centerBox.getChildren().add(productCntrLabel);

        HBox.setHgrow(centerBox, Priority.ALWAYS);
        columns.getChildren().add(centerBox);

        setCenterContent(columns);
    }


    public boolean isShowDescription() {
        return showDescription.get();
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription.set(showDescription);
    }

    public StyleableObjectProperty<Boolean> showDescriptionProperty() {
        return showDescription;
    }
    public Number getTextSpacing() {
        return textSpacing.get();
    }

    public StyleableObjectProperty<Number> textSpacingProperty() {
        return textSpacing;
    }

    public void setTextSpacing(Number textSpacing) {
        this.textSpacing.set(textSpacing);
    }

    public long getShipmentId() {
        return shipmentId.get();
    }

    public LongProperty shipmentIdProperty() {
        return shipmentId;
    }

    public void setShipmentId(long shipmentId) {
        this.shipmentId.set(shipmentId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getProductCntr() {
        return productCntr.get();
    }

    public LongProperty productCntrProperty() {
        return productCntr;
    }

    public void setProductCntr(long productCntr) {
        this.productCntr.set(productCntr);
    }

    private static class StyleableProperties {
        private static final DefaultPropertyBasedCssMetaData<ShipmentsFxListCell, Boolean> SHOW_DESCRIPTION = CssHelper.createMetaData("-fx-show-description", StyleConverter.getBooleanConverter(), "showDescription", Boolean.FALSE);
        private static final DefaultPropertyBasedCssMetaData<ShipmentsFxListCell, Number> TEXT_SPACING = CssHelper.createMetaData("-fx-text-spacing", StyleConverter.getSizeConverter(), "textSpacing", 0);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), SHOW_DESCRIPTION, TEXT_SPACING);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return ShipmentsFxListCell.StyleableProperties.STYLEABLES;
    }

}
