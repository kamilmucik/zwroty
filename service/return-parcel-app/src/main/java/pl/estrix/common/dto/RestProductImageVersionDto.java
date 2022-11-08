package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.common.dto.model.ProductImageVersionDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestProductImageVersionDto {

    private List<ProductImageVersionDto> results;
}
