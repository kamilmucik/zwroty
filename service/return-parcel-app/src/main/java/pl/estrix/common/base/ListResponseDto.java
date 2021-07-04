package pl.estrix.common.base;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.annotation.CheckForNull;
import java.util.List;

/**
 * Handle response from Gateways with list and total count of records.
 *
 * @param <T>
 *        type of data of result elements
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponseDto<T> {
    private Integer totalCount;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @CheckForNull
    public T elementAt(int index) {
        return (index >= 0 && !isEmpty() && data.size() > index) ? data.get(index) : null;
    }

}