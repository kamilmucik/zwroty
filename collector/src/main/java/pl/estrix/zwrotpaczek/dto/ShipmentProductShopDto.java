package pl.estrix.zwrotpaczek.dto;

public class ShipmentProductShopDto extends BaseEntityDto<Long>{

    private Long productId;
    private Long shopNumber;
    private Long recognitionNumber;
    private Long recognitionCounter;

    public ShipmentProductShopDto() {
    }

    public ShipmentProductShopDto(Long productId, Long shopNumber, Long recognitionNumber, Long recognitionCounter) {
        this.productId = productId;
        this.shopNumber = shopNumber;
        this.recognitionNumber = recognitionNumber;
        this.recognitionCounter = recognitionCounter;
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

    @Override
    public String toString() {
        return "ShipmentProductShopDto{" +
                "productId=" + productId +
                ", shopNumber=" + shopNumber +
                ", recognitionNumber=" + recognitionNumber +
                ", recognitionCounter=" + recognitionCounter +
                '}';
    }
}
