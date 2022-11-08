package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestProductImageVersionRevisionDto {

    private String message;
    private ProductImageVersionRevisionDto dto;
}
