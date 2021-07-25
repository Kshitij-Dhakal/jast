package dhaka.example.blog;

import java.util.Collections;
import java.util.List;

public class PageResponse<T> {
    private List<T> tList;
    private Long offset;
    private Long limit;
    private Sort sort;

    public PageResponse(List<T> tList, Long offset, Long limit, Sort sort) {
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
