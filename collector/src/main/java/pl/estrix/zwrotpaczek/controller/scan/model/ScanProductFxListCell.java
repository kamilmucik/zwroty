package pl.estrix.zwrotpaczek.controller.scan.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.estrix.zwrotpaczek.ui.CssHelper;
import pl.estrix.zwrotpaczek.ui.DefaultPropertyBasedCssMetaData;
import pl.estrix.zwrotpaczek.ui.StructuredListCell;

import java.util.List;

public class ScanProductFxListCell<T> extends StructuredListCell<T> {

    private Label nameLabel;
    private Label eanLabel;
    private Label storeLabel;
    private Label cntAllLabel;
    private Label cntScanCorrectLabel;
    private Label cntScanErrorLabel;

    private VBox centerBox;
    private VBox rightBox;

    private HBox row2;
    private HBox columns;

    private StringProperty name;
    private StringProperty ean;
    private StringProperty store;
    private LongProperty cntAll;
    private LongProperty cntScanCorrect;
    private LongProperty cntScanError;

    private StyleableObjectProperty<Boolean> showDescription;

    private StyleableObjectProperty<Number> textSpacing;

    public ScanProductFxListCell(){
        getStyleClass().add("scan-media-list-cell");
        name = new SimpleStringProperty();
        ean = new SimpleStringProperty();
        store = new SimpleStringProperty();
        cntAll = new SimpleLongProperty();
        cntScanCorrect = new SimpleLongProperty();
        cntScanError = new SimpleLongProperty();
        columns = new HBox();

        textSpacing = CssHelper.createProperty(ScanProductFxListCell.StyleableProperties.TEXT_SPACING, this);
        showDescription = CssHelper.createProperty(ScanProductFxListCell.StyleableProperties.SHOW_DESCRIPTION, this);

        centerBox = new VBox();
        centerBox.spacingProperty().bind(textSpacing);
        centerBox.setFillWidth(true);
        centerBox.getStyleClass().add("scan-box-center");

        rightBox = new VBox();
        rightBox.setFillWidth(true);
        rightBox.getStyleClass().add("scan-box-right");

        nameLabel = new Label();
        nameLabel.getStyleClass().add("media-cell-title");
        nameLabel.textProperty().bind(name);
        centerBox.getChildren().add(nameLabel);

        eanLabel = new Label();
        eanLabel.getStyleClass().add("media-cell-amount");
        eanLabel.textProperty().bind(ean);
        centerBox.getChildren().add(eanLabel);

        storeLabel = new Label();
        storeLabel.getStyleClass().add("media-cell-amount");
        storeLabel.textProperty().bind(store);
        centerBox.getChildren().add(storeLabel);

        cntAllLabel = new Label();
        cntAllLabel.getStyleClass().add("scan-cnt-all");
        cntAllLabel.textProperty().bind(cntAll.asString());
        AnchorPane cntAll = new AnchorPane();
        cntAll.getChildren().add(cntAllLabel);
        AnchorPane.setLeftAnchor(cntAll, 0.0);
        AnchorPane.setRightAnchor(cntAll, 0.0);
        rightBox.getChildren().add(cntAll);

        cntScanCorrectLabel = new Label();
        cntScanCorrectLabel.getStyleClass().add("scan-cnt-correct");
        cntScanCorrectLabel.textProperty().bind(cntScanCorrect.asString());

        cntScanErrorLabel = new Label();
        cntScanErrorLabel.getStyleClass().add("scan-cnt-error");
        cntScanErrorLabel.textProperty().bind(cntScanError.asString());

        rightBox.getChildren().add(new HBox(cntScanCorrectLabel,cntScanErrorLabel ));

        columns.getChildren().add(centerBox);
//        columns.getChildren().add(rightBox);

        setCenterContent(columns);
        setRightContent(rightBox);

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

    public VBox getCenterBox() {
        return centerBox;
    }

    public void setCenterBox(VBox centerBox) {
        this.centerBox = centerBox;
    }

    public HBox getRow2() {
        return row2;
    }

    public void setRow2(HBox row2) {
        this.row2 = row2;
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

    public String getEan() {
        return ean.get();
    }

    public StringProperty eanProperty() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean.set(ean);
    }

    public Boolean getShowDescription() {
        return showDescription.get();
    }

    public StyleableObjectProperty<Boolean> showDescriptionProperty() {
        return showDescription;
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

    public String getStore() {
        return store.get();
    }

    public StringProperty storeProperty() {
        return store;
    }

    public void setStore(String store) {
        this.store.set(store);
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

    public long getCntScanCorrect() {
        return cntScanCorrect.get();
    }

    public LongProperty cntScanCorrectProperty() {
        return cntScanCorrect;
    }

    public void setCntScanCorrect(long cntScanCorrect) {
        this.cntScanCorrect.set(cntScanCorrect);
    }

    public long getCntScanError() {
        return cntScanError.get();
    }

    public LongProperty cntScanErrorProperty() {
        return cntScanError;
    }

    public void setCntScanError(long cntScanError) {
        this.cntScanError.set(cntScanError);
    }

    private static class StyleableProperties {
        private static final DefaultPropertyBasedCssMetaData<ScanProductFxListCell, Boolean> SHOW_DESCRIPTION = CssHelper.createMetaData("-fx-show-description", StyleConverter.getBooleanConverter(), "showDescription", Boolean.FALSE);
        private static final DefaultPropertyBasedCssMetaData<ScanProductFxListCell, Number> TEXT_SPACING = CssHelper.createMetaData("-fx-text-spacing", StyleConverter.getSizeConverter(), "textSpacing", 0);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), SHOW_DESCRIPTION, TEXT_SPACING);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
