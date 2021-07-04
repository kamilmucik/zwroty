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
public class PrintDto extends BaseEntityDto<Long> {

    private String fileName;
}
