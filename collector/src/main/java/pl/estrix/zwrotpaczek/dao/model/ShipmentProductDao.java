package pl.estrix.zwrotpaczek.dao.model;

import java.util.Date;

public class ShipmentProductDao extends BaseDao {

    private Integer id;

    private Long artNumber;

    private String name;

    private Long counter;

    private String companyName;

    private String artReturn;

    private String ean;

    private Double weight;

    private Double artVolume;

    private Long shipmentId;

    private Long scanCorrect;

    private Long scanError;
    private Long scanLabel;

    private String store;

    private Date update;

    private Boolean sended;
    private Long shopCounter;

    public ShipmentProductDao() {
        this.update = new Date();
        this.sended = false;
        this.shopCounter = 0L;
    }

    public ShipmentProductDao(Integer id,
                              Long artNumber,
                              String name,
                              Long counter,
                              String companyName,
                              String artReturn,
                              String ean,
                              Double weight,
                              Double artValume,
                              Long shipmentId,
                              Long scanCorrect,
                              Long scanError,
                              String store
    ) {
        this.id = id;
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artVolume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.store = store;
        this.update = new Date();
        this.sended = false;
        this.shopCounter = 0L;
    }

    public ShipmentProductDao(Long artNumber,
                              String name,
                              Long counter,
                              String companyName,
                              String artReturn,
                              String ean,
                              Double weight,
                              Double artValume,
                              Long shipmentId,
                              Long scanCorrect,
                              Long scanError,
                              String store,
                              Long shopCounter
    ) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artVolume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.store = store;
        this.update = new Date();
        this.sended = false;
        this.shopCounter = shopCounter;
    }

    public ShipmentProductDao(Long artNumber,
                              String name,
                              Long counter,
                              String companyName,
                              String artReturn,
                              String ean,
                              Double weight,
                              Double artValume,
                              Long shipmentId,
                              Long scanCorrect,
                              Long scanError,
                              Long scanLabel,
                              String store,
                              Long shopCounter
    ) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artVolume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.scanLabel = scanLabel;
        this.store = store;
        this.update = new Date();
        this.sended = false;
        this.shopCounter = shopCounter;
    }

    public ShipmentProductDao(Integer id, Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artVolume, Long shipmentId, Long scanCorrect, Long scanError, Long scanLabel, String store, Date update, Boolean sended, Long shopCounter) {
        this.id = id;
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artVolume = artVolume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.scanLabel = scanLabel;
        this.store = store;
        this.update = update;
        this.sended = sended;
        this.shopCounter = shopCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(Long artNumber) {
        this.artNumber = artNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getArtReturn() {
        return artReturn;
    }

    public void setArtReturn(String artReturn) {
        this.artReturn = artReturn;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getArtVolume() {
        return artVolume;
    }

    public void setArtVolume(Double artValume) {
        this.artVolume = artValume;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getScanCorrect() {
        return scanCorrect;
    }

    public void setScanCorrect(Long scanCorrect) {
        this.scanCorrect = scanCorrect;
    }

    public Long getScanError() {
        return scanError;
    }

    public void setScanError(Long scanError) {
        this.scanError = scanError;
    }

    public Long getScanLabel() {
        return scanLabel;
    }

    public void setScanLabel(Long scanLabel) {
        this.scanLabel = scanLabel;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Boolean getSended() {
        return sended;
    }

    public void setSended(Boolean sended) {
        this.sended = sended;
    }

    public Long getShopCounter() {
        return shopCounter;
    }

    public void setShopCounter(Long shopCounter) {
        this.shopCounter = shopCounter;
    }

    @Override
    public String toString() {
        return "ShipmentProductDao{" +
                "id=" + id +
                ", artNumber=" + artNumber +
                ", counter=" + counter +
                ", scanCorrect=" + scanCorrect +
                ", scanError=" + scanError +
                ", update=" + update +
                ", shopCounter=" + shopCounter +
                '}';
    }
}
