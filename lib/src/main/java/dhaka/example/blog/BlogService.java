package dhaka.example.blog;

import java.util.Optional;

class BlogService {
    private final BlogRepo blogRepo;

    public BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    Optional<Blog> updateBlog(Blog blog) {
        return blogRepo.transactional(txn -> {
            var errMsg = "Could not update blog.";
            try {
                if (blogRepo.delete(blog) &&
                        blogRepo.save(blog)) {
                    txn.commit();
                    return blogRepo.findById(blog.getId());
                } else {
                    txn.rollback();
                    throw new FailedException(errMsg);
                }
            } catch (FailedException throwable) {
                //rethrow failed exception
                txn.rollback();
                throw throwable;
            } catch (Throwable throwable) {
                //wrap all the other exceptions in failed exception
                txn.rollback();
                throw new FailedException(errMsg, throwable);
            }
        });
    }
}
