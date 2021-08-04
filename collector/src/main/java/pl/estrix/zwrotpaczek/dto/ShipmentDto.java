package pl.estrix.zwrotpaczek.dto;

public class ShipmentDto extends BaseEntityDto<Long> {

    private Long number;

    private Integer productCounter;

    private Boolean active;

    private Integer group;

    public ShipmentDto() {}

    public ShipmentDto(Long number) {
        this.number = number;
    }

    public ShipmentDto(Long number,
                       Integer productCounter,
                       Boolean active,
                       Integer group
    ) {
        this.number = number;
        this.productCounter = productCounter;
        this.active = active;
        this.group = group;
    }

    public ShipmentDto(Long id,
                       Long number,
                       Integer productCounter,
                       Boolean active,
                       Integer group,
                       String label) {
        this.number = number;
        this.productCounter = productCounter;
        this.active = active;
        this.group = group;
        this.setId(id);
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getProductCounter() {
        return productCounter;
    }

    public void setProductCounter(Integer productCounter) {
        this.productCounter = productCounter;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "ShipmentDto{" +
                "number=" + number +
                ", productCounter=" + productCounter +
                '}';
    }
}