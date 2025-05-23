package pl.estrix.backend.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityDto<T> extends BaseDto implements EntityDto<T> {

    private T id;

    private String label;

    public String getLabel() {
        return label;
    }

    public BaseEntityDto(T id) {
        this.id = id;
    }
}
