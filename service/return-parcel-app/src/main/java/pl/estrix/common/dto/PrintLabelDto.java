package pl.estrix.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PrintLabelDto extends BaseEntityDto<Long> {

    private Long artNumber;
    private String palletOption;
    private String returnNumber;
    private Long counter;
    private String author;
    private Long collectorId;
    private Integer palletCounter = 1;

    public void setLabel(String label) {
        super.setLabel(label);
    }

}
