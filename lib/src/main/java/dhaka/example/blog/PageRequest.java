package dhaka.example.blog;

import java.util.List;

enum Sort {
    ASC("ASC"),
    DESC("DESC");
    private final String query;

    Sort(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

class PageRequest {
    private final long offset;
    private final long limit;
    private final Sort sort;

    PageRequest(long offset, long limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public long getOffset() {
        return offset;
    }

    public long getLimit() {
        return limit;
    }

    public Sort getSort() {
        return sort;
    }

    public  <T> PageResponse<T> buildResponse(List<T> values) {
        return new PageResponse<>(values, offset, limit, sort);
    }
}
