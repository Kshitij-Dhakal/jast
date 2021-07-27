package dhaka.example.blog;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dhaka.jast.Row;
import dhaka.jast.SqlRepo;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlogRepo extends SqlRepo {

    public BlogRepo(DataSource dataSource) {
        super(dataSource);
    }

    PageResponse<Blog> findAll(PageRequest request) {
        //get total count
        var count = sql("SELECT COUNT(DISTINCT b.id) FROM blog b")
                .withConverter(row -> row.getLong(1))
                .findFirst().rethrowError().orElse(0L);

        if (count == 0) {
            return PageResponse.empty();
        }

        //get paginated id list
        var sort = request.getSort().getQuery();
        var idList = sql("SELECT DISTINCT b.id, b.created FROM blog b ORDER BY b.created " + sort +
                " LIMIT b.created ?, ?")
                .bind(1, request.getOffset())
                .bind(2, request.getLimit())
                .withConverter(row -> row.getString("b.id"))
                .findAll().rethrowError();
        if (idList.isEmpty()) {
            return PageResponse.empty();
        }

        //select blogs by idList
        var sql = sql("SELECT b.id, b.content, b.created, p.id, p.name, c.id, c.content " +
                "FROM blog b LEFT JOIN publisher p ON p.id=b.publisher LEFT JOIN comment c ON c.blog=b.id " +
                "WHERE b.id IN (?" + ", ?".repeat(idList.size() - 1) + ") ORDER BY b.created " + sort);
        for (int i = 0; i < idList.size(); i++) {
            sql.bind(i + 1, idList.get(i));
        }
        var all = sql.withConverter(this::toBlog)
                .findAll().rethrowError().stream()
                //group blogs by id
                .collect(Collectors.groupingBy(Blog::getId, LinkedHashMap::new, Collectors.toList()))
                //collect comments with common blog id
                .values().stream().map(this::collectComments)
                .collect(Collectors.toList());

        return request.buildResponse(all);
    }

    private Blog collectComments(List<Blog> blogs) {
        Map<String, Comment> commentList = blogs.stream()
                .flatMap(blog -> blog.getComments().stream())
                .collect(Collectors.toMap(Comment::getId, comment -> comment, (a, b) -> b, Maps::newLinkedHashMap));

        return blogs.get(0).toBuilder()
                .setComments(Lists.newArrayList(commentList.values()))
                .build();
    }

    private Blog toBlog(Row row) {
        var blogBuilder = Blog.newBuilder();
        blogBuilder.setId(row.getString("b.id"))
                .setContent(row.getString("b.content"))
                .setCreated(row.getLong("b.created"));

        var publisherBuilder = Publisher.newBuilder();
        publisherBuilder.setId(row.getString("p.id"))
                .setName(row.getString("p.name"));
        blogBuilder.setPublisher(publisherBuilder.build());

        var commentId = row.getString("c.id");
        if (commentId != null) {
            var commentBuilder = Comment.newBuilder();
            commentBuilder.setId(commentId)
                    .setContent(row.getString("c.content"));
            blogBuilder.setComments(List.of(commentBuilder.build()));
        }
        return blogBuilder.build();
    }
}
