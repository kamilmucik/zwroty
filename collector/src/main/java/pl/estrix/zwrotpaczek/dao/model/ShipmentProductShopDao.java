package pl.estrix.zwrotpaczek.dao.model;

public class ShipmentProductShopDao extends BaseDao {


    private Integer id;
    private String name;
    private Integer productId;
    private Long shopNumber;
    private Long artNumber;
    private Long recognitionNumber;
    private Long shipNumber;
    private Long recognitionCounter;
    private String artReturn;

    public ShipmentProductShopDao() {
    }

    public ShipmentProductShopDao(Integer id, Integer productId, Long shopNumber, Long recognitionNumber, Long recognitionCounter) {
        this.id = id;
        this.name = "nazwa";
        this.productId = productId;
        this.shopNumber = shopNumber;
        this.recognitionNumber = recognitionNumber;
        this.recognitionCounter = recognitionCounter;
    }

    public ShipmentProductShopDao(Integer id, String name, Integer productId, Long shopNumber, Long recognitionNumber, Long recognitionCounter) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.shopNumber = shopNumber;
        this.recognitionNumber = recognitionNumber;
        this.recognitionCounter = recognitionCounter;
    }

    public ShipmentProductShopDao(Integer id, String name, Integer productId, Long shopNumber, Long artNumber, Long recognitionNumber, Long shipNumber, Long recognitionCounter, String artReturn) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.shopNumber = shopNumber;
        this.artNumber = artNumber;
        this.recognitionNumber = recognitionNumber;
        this.shipNumber = shipNumber;
        this.recognitionCounter = recognitionCounter;
        this.artReturn = artReturn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(Long shopNumber) {
        this.shopNumber = shopNumber;
    }

    public Long getRecognitionNumber() {
        return recognitionNumber;
    }

    public void setRecognitionNumber(Long recognitionNumber) {
        this.recognitionNumber = recognitionNumber;
    }

    public Long getRecognitionCounter() {
        return recognitionCounter;
    }

    public void setRecognitionCounter(Long recognitionCounter) {
        this.recognitionCounter = recognitionCounter;
    }

    public Long getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(Long artNumber) {
        this.artNumber = artNumber;
    }

    public Long getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(Long shipNumber) {
        this.shipNumber = shipNumber;
    }

    public String getArtReturn() {
        return artReturn;
    }

    public void setArtReturn(String artReturn) {
        this.artReturn = artReturn;
    }
}
