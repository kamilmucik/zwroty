package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShipmentProductShopDto extends BaseEntityDto<Long> {


    private Long productId;
    private Long shopNumber;
    private Long artNumber;
    private Long recognitionNumber;
    private Long shipNumber;
    private Long recognitionCounter;
    private String artReturn;
    private Long scanCorrect;
    private Long scanError;
    private Long scanLabel;
    private Boolean archivated;

    @Override
    public String toString() {
        return "ShipmentProductShopDto{" +
                "shopNumber=" + shopNumber +
                ", artNumber=" + artNumber +
                ", recognitionNumber=" + recognitionNumber +
                ", shipNumber=" + shipNumber +
                ", recognitionCounter=" + recognitionCounter +
                '}';
    }
}
