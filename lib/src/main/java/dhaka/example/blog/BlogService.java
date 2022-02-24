package dhaka.example.blog;

class BlogService {
    private final BlogRepo blogRepo;

    BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }
}
