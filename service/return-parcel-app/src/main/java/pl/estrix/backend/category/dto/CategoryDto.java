package pl.estrix.backend.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseEntityDto<Long> {

    private String name;
}
