package pl.estrix.zwrotpaczek.dto;

import java.util.List;

public class ShopListItemDto extends BaseEntityDto<Long>{


    private List<ShopDto> shops;

    public ShopListItemDto(List<ShopDto> shops) {
        this.shops = shops;
    }
    public ShopListItemDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    public List<ShopDto> getShops() {
        return shops;
    }

    public void setShops(List<ShopDto> shops) {
        this.shops = shops;
    }

    @Override
    public String toString() {
        return "ShopListItemDto{" +
                "shops=" + shops +
                '}';
    }
}
