package pl.estrix.zwrotpaczek.controller.shipments.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class ShipmentsFxImpl implements ShipmentsFx {

    private LongProperty shipmentId;

    private StringProperty name;
    private StringProperty selectedName;

    private LongProperty productCntr;

    private ObjectProperty<Image> image;

    private BooleanProperty switchedOn;

    public ShipmentsFxImpl(Long shipmentId, String name, Long productCntr) {
        this.shipmentId = new SimpleLongProperty(shipmentId);
        this.name = new SimpleStringProperty(name);
        this.selectedName = new SimpleStringProperty("");
        this.productCntr = new SimpleLongProperty(productCntr);
        this.switchedOn = new SimpleBooleanProperty(false);
        this.image = new SimpleObjectProperty<>(new Image(getClass().getResource("/images/Arrow-Right-icon.png").toExternalForm()));
    }

    public long getShipmentId() {
        return shipmentId.get();
    }

    public void setShipmentId(long shipmentId) {
        this.shipmentId.set(shipmentId);
    }

    public long getProductCntr() {
        return productCntr.get();
    }

    public void setProductCntr(long productCntr) {
        this.productCntr.set(productCntr);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getProductCnt() {
        return productCntr.get();
    }

    public void setProductCnt(long productCnt) {
        this.productCntr.set(productCnt);
    }

    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public boolean isSwitchedOn() {
        return switchedOn.get();
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn.set(switchedOn);
    }

    public String getSelectedName() {
        return selectedName.get();
    }

    public void setSelectedName(String selectedName) {
        this.selectedName.set(selectedName);
    }

    @Override
    public LongProperty shipmentIdProperty() {
        return shipmentId;
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public StringProperty selectedNameProperty() {
        return selectedName;
    }

    @Override
    public LongProperty productCntrProperty() {
        return productCntr;
    }

    @Override
    public BooleanProperty switchedOnProperty() {
        return switchedOn;
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return image;
    }
}
