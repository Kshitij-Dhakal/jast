package dhaka.example.blog;

import java.util.Collections;
import java.util.List;

class PageResponse<T> {
    private final List<T> tList;
    private final Long offset;
    private final Long limit;
    private final Sort sort;

    PageResponse(List<T> tList, Long offset, Long limit, Sort sort) {
        this.tList = tList;
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static <T> PageResponse<T> empty() {
        return new PageResponse<>(Collections.emptyList(), 0L, 0L, Sort.DESC);
    }

    public List<T> getData() {
        return tList;
    }

    public Long getOffset() {
        return offset;
    }

    public Long getLimit() {
        return limit;
    }

    public Sort getSort() {
        return sort;
    }
}
