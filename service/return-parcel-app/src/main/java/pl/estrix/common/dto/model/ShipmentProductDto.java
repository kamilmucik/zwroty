package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShipmentProductDto extends BaseEntityDto<Long> {

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

    private LocalDateTime lastUpdate;

    private Boolean sended;

    private Long shopNr;
    private Long shopCounter;
    private String palletOption;

    private List<ShipmentProductShopDto> shops = new ArrayList<>();

    public void addEan(String ean){
        this.ean = this.ean + ean;
    }
    public void addShop(ShipmentProductShopDto shop){
        this.shops.add(shop);
    }
    public void setShopCounter(Long cnt){
        this.shopCounter = cnt;
    }
}
