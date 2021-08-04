package pl.estrix.zwrotpaczek.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipmentProductDto extends BaseEntityDto<Long>{

    private Long artNumber;

    private String name;

    private Long counter;

    private String companyName;

    private String artReturn;

    private String ean;

    private Double weight;

    private Double artValume;

    private Long shipmentId;

    private Long scanCorrect;

    private Long scanError;

    private Long scanLabel;

    private String store;

    private String scanLog;

    private Date update;

    private Boolean sended;

    private Long shopNr;

    private Long shopCounter;

    private List<ShipmentProductShopDto> shops = new ArrayList<>();

    public ShipmentProductDto() {}

    public ShipmentProductDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    public ShipmentProductDto(Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artValume, Long shipmentId, Long scanCorrect, Long scanError, Long scanLabel, String store, Date update, Boolean sended, Long shopNr, Long shopCounter, List<ShipmentProductShopDto> shops) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.scanLabel = scanLabel;
        this.store = store;
        this.update = update;
        this.sended = sended;
        this.shopNr = shopNr;
        this.shopCounter = shopCounter;
        this.shops = shops;
    }

    public ShipmentProductDto(Long artNumber,
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
                              Boolean sended
    ) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.store = store;
        this.update = new Date();
        this.sended = sended;
    }

    public ShipmentProductDto(Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artValume, Long shipmentId, Long scanCorrect, Long scanError, String store, Date update, Boolean sended, List<ShipmentProductShopDto> shops) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.store = store;
        this.update = update;
        this.sended = sended;
        this.shops = shops;
    }

    public ShipmentProductDto(Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artValume, Long shipmentId, Long scanCorrect, Long scanError, String store, Date update, Boolean sended, Long shopNr, List<ShipmentProductShopDto> shops) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.store = store;
        this.update = update;
        this.sended = sended;
        this.shopNr = shopNr;
        this.shops = shops;
    }

    public ShipmentProductDto(Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artValume, Long shipmentId, Long scanCorrect, Long scanError, Long scanLabel, String store, Date update, Boolean sended, Long shopNr, List<ShipmentProductShopDto> shops) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.scanLabel = scanLabel;
        this.store = store;
        this.update = update;
        this.sended = sended;
        this.shopNr = shopNr;
        this.shops = shops;
    }

    public ShipmentProductDto(Long artNumber, String name, Long counter, String companyName, String artReturn, String ean, Double weight, Double artValume, Long shipmentId, Long scanCorrect, Long scanError, Long scanLabel, String store, String scanLog, Date update, Boolean sended, Long shopNr, Long shopCounter, List<ShipmentProductShopDto> shops) {
        this.artNumber = artNumber;
        this.name = name;
        this.counter = counter;
        this.companyName = companyName;
        this.artReturn = artReturn;
        this.ean = ean;
        this.weight = weight;
        this.artValume = artValume;
        this.shipmentId = shipmentId;
        this.scanCorrect = scanCorrect;
        this.scanError = scanError;
        this.scanLabel = scanLabel;
        this.store = store;
        this.scanLog = scanLog;
        this.update = update;
        this.sended = sended;
        this.shopNr = shopNr;
        this.shopCounter = shopCounter;
        this.shops = shops;
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

    public Double getArtValume() {
        return artValume;
    }

    public void setArtValume(Double artValume) {
        this.artValume = artValume;
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

    public List<ShipmentProductShopDto> getShops() {
        return shops;
    }

    public void setShops(List<ShipmentProductShopDto> shops) {
        this.shops = shops;
    }

    public Long getShopNr() {
        return shopNr;
    }

    public void setShopNr(Long shopNr) {
        this.shopNr = shopNr;
    }

    public Long getShopCounter() {
        return shopCounter;
    }

    public void setShopCounter(Long shopCounter) {
        this.shopCounter = shopCounter;
    }

    public String getScanLog() {
        return scanLog;
    }

    public void setScanLog(String scanLog) {
        this.scanLog = scanLog;
    }
}
