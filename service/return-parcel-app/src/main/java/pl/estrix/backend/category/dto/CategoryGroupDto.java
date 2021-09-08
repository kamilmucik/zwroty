package pl.estrix.backend.category.dto;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@MappedSuperclass
public class CategoryGroupDto extends BaseEntityDto<Long> {

    private String name;

    private List<CategoryDto> categories = new ArrayList<>();
}
