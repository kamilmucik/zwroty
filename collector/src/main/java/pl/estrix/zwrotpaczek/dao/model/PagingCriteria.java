package pl.estrix.zwrotpaczek.dao.model;

public class PagingCriteria {

    Integer page;
    int start;
    Integer limit;

    public static PagingCriteria empty() {
        return EMPTY;
    }

    public PagingCriteria() {
    }

    public PagingCriteria(Integer page, int start, Integer limit) {
        this.page = page;
        this.start = start;
        this.limit = limit;
    }

    private static final PagingCriteria EMPTY = new PagingCriteria() {

        private final RuntimeException uOpException = new UnsupportedOperationException("This is an immutable instance of PagingCriteria");

        public void setLimit(Integer limit) {
            throw uOpException;
        };

        public void setPage(Integer page) {
            throw uOpException;
        };

        public void setStart(int start) {
            throw uOpException;
        };

    };

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
