package pl.estrix.model;

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

}
