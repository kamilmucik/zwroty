package pl.estrix.zwrotpaczek.controller.dialog.scanlist.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.estrix.zwrotpaczek.ui.CssHelper;
import pl.estrix.zwrotpaczek.ui.DefaultPropertyBasedCssMetaData;
import pl.estrix.zwrotpaczek.ui.StructuredListCell;

import java.util.List;

public class ScanListFxListCell <T> extends StructuredListCell<T> {

    private Label nameLabel;
    private Label eanLabel;
    private Label cntAllLabel;
    private VBox centerBox;

    private StringProperty name;
    private StringProperty ean;
    private LongProperty cntAll;

    private StyleableObjectProperty<Boolean> showDescription;
    private StyleableObjectProperty<Number> textSpacing;

    public ScanListFxListCell(){
        getStyleClass().add("scan-media-list-cell");
        name = new SimpleStringProperty();
        ean = new SimpleStringProperty();
        cntAll = new SimpleLongProperty();

        textSpacing = CssHelper.createProperty(ScanListFxListCell.StyleableProperties.TEXT_SPACING, this);
        showDescription = CssHelper.createProperty(ScanListFxListCell.StyleableProperties.SHOW_DESCRIPTION, this);
        centerBox = new VBox();
        centerBox.spacingProperty().bind(textSpacing);

        nameLabel = new Label();
        nameLabel.getStyleClass().add("media-cell-title");
        nameLabel.textProperty().bind(name);
        centerBox.getChildren().add(nameLabel);

        eanLabel = new Label();
        eanLabel.getStyleClass().add("media-cell-title");
        eanLabel.textProperty().bind(ean);
        centerBox.getChildren().add(eanLabel);

        cntAllLabel = new Label();
        cntAllLabel.getStyleClass().add("media-cell-title");
        cntAllLabel.textProperty().bind(cntAll.asString());
        centerBox.getChildren().add(cntAllLabel);

        setCenterContent(centerBox);

    }
    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getEanLabel() {
        return eanLabel;
    }

    public void setEanLabel(Label eanLabel) {
        this.eanLabel = eanLabel;
    }

    public Label getCntAllLabel() {
        return cntAllLabel;
    }

    public void setCntAllLabel(Label cntAllLabel) {
        this.cntAllLabel = cntAllLabel;
    }

    public String getEan() {
        return ean.get();
    }

    public StringProperty eanProperty() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean.set(ean);
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

    public long getCntAll() {
        return cntAll.get();
    }

    public LongProperty cntAllProperty() {
        return cntAll;
    }

    public void setCntAll(long cntAll) {
        this.cntAll.set(cntAll);
    }


    public void setShowDescription(Boolean showDescription) {
        this.showDescription.set(showDescription);
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

    private static class StyleableProperties {
        private static final DefaultPropertyBasedCssMetaData<ScanListFxListCell, Boolean> SHOW_DESCRIPTION = CssHelper.createMetaData("-fx-show-description", StyleConverter.getBooleanConverter(), "showDescription", Boolean.FALSE);
        private static final DefaultPropertyBasedCssMetaData<ScanListFxListCell, Number> TEXT_SPACING = CssHelper.createMetaData("-fx-text-spacing", StyleConverter.getSizeConverter(), "textSpacing", 0);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), SHOW_DESCRIPTION, TEXT_SPACING);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return ScanListFxListCell.StyleableProperties.STYLEABLES;
    }
}
