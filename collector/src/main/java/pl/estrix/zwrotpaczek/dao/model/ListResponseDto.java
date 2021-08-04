package pl.estrix.zwrotpaczek.dao.model;

import java.util.List;

public class ListResponseDto<T> {

    private Integer totalCount;
    private List<T> data;

    public ListResponseDto(Integer totalCount, List<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setData(List<T> data) {
        this.data = data;
    }



    public List<T> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
