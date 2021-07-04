package pl.estrix.zwrotpaczek.controller.scan.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Date;

public class ScanProductFxImpl implements ScanProductFx {

    private LongProperty id;

    private StringProperty name;

    private StringProperty ean;

    private StringProperty store;

    private LongProperty cntAll;

    private LongProperty cntCorrect;

    private LongProperty cntError;

    private ObjectProperty<Image> image;

    public ScanProductFxImpl(Long id,String name, String ean, Long cntAll, Long cntCorrect, Long cntError, String store) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.ean = new SimpleStringProperty(ean);
        this.store = new SimpleStringProperty(store);
        this.cntAll = new SimpleLongProperty(cntAll);
        this.cntCorrect = new SimpleLongProperty(cntCorrect);
        this.cntError = new SimpleLongProperty(cntError);
        this.image = new SimpleObjectProperty<>(new Image(getClass().getResource("/images/Arrow-Right-icon.png").toExternalForm()));
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEan() {
        return ean.get();
    }

    public void setEan(String ean) {
        this.ean.set(ean);
    }

    public long getCntAll() {
        return cntAll.get();
    }

    public void setCntAll(long cntAll) {
        this.cntAll.set(cntAll);
    }

    public long getCntCorrect() {
        return cntCorrect.get();
    }

    public void setCntCorrect(long cntCorrect) {
        this.cntCorrect.set(cntCorrect);
    }

    public long getCntError() {
        return cntError.get();
    }

    public void setCntError(long cntError) {
        this.cntError.set(cntError);
    }

    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public String getStore() {
        return store.get();
    }

    public void setStore(String store) {
        this.store.set(store);
    }

    @Override
    public LongProperty idProperty() {
        return id;
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public StringProperty eanProperty() {
        return ean;
    }

    @Override
    public StringProperty storeProperty() {
        return store;
    }

    @Override
    public LongProperty cntAllProperty() {
        return cntAll;
    }

    @Override
    public LongProperty cntCorrectProperty() {
        return cntCorrect;
    }

    @Override
    public LongProperty cntErrorProperty() {
        return cntError;
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return image;
    }

}
