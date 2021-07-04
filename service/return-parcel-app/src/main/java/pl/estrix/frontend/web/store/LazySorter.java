package pl.estrix.frontend.web.store;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
import pl.estrix.common.dto.model.StoreDto;

public class LazySorter implements Comparator<StoreDto> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(StoreDto car1, StoreDto car2) {
        try {
            Object value1 = StoreDto.class.getField(this.sortField).get(car1);
            Object value2 = StoreDto.class.getField(this.sortField).get(car2);

            int value = ((Comparable)value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}