package pl.estrix.zwrotpaczek.dto;

public class ShopDto extends BaseEntityDto<Long> {

    private Long productId;
    private Long shopNumber;
    private Long artNumber;
    private Long recognitionNumber;
    private Long shipNumber;
    private Long recognitionCounter;
    private String artReturn;

    public ShopDto() {
    }

    public ShopDto(Long productId, Long shopNumber, Long artNumber, Long recognitionNumber, Long shipNumber, Long recognitionCounter, String artReturn) {
        this.productId = productId;
        this.shopNumber = shopNumber;
        this.artNumber = artNumber;
        this.recognitionNumber = recognitionNumber;
        this.shipNumber = shipNumber;
        this.recognitionCounter = recognitionCounter;
        this.artReturn = artReturn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(Long shopNumber) {
        this.shopNumber = shopNumber;
    }

    public Long getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(Long artNumber) {
        this.artNumber = artNumber;
    }

    public Long getRecognitionNumber() {
        return recognitionNumber;
    }

    public void setRecognitionNumber(Long recognitionNumber) {
        this.recognitionNumber = recognitionNumber;
    }

    public Long getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(Long shipNumber) {
        this.shipNumber = shipNumber;
    }

    public Long getRecognitionCounter() {
        return recognitionCounter;
    }

    public void setRecognitionCounter(Long recognitionCounter) {
        this.recognitionCounter = recognitionCounter;
    }

    public String getArtReturn() {
        return artReturn;
    }

    public void setArtReturn(String artReturn) {
        this.artReturn = artReturn;
    }

    @Override
    public String toString() {
        return "ShopDto{" +
                "productId=" + productId +
                ", shopNumber=" + shopNumber +
                ", artNumber=" + artNumber +
                ", recognitionNumber=" + recognitionNumber +
                ", shipNumber=" + shipNumber +
                ", recognitionCounter=" + recognitionCounter +
                ", artReturn='" + artReturn + '\'' +
                '}';
    }
}
