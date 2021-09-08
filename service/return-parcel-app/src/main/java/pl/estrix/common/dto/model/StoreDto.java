package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreDto  extends BaseEntityDto<Long> {

    private Double minVolume;
    private Double maxVolume;
    private String place;
    private String description;
    private Integer group;

}
