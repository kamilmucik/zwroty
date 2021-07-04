package pl.estrix.zwrotpaczek.controller.dialog.scanlist.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScanListFxImpl implements ScanListFx {

    private LongProperty id;

    private StringProperty name;

    private StringProperty ean;

    private LongProperty cntAll;

    public ScanListFxImpl(Long id,String name, String ean, Long cntAll) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.ean = new SimpleStringProperty(ean);
        this.cntAll = new SimpleLongProperty(cntAll);
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
    public LongProperty cntAllProperty() {
        return cntAll;
    }

}
