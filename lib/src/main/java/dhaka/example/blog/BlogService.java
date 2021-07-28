package dhaka.example.blog;

import dhaka.jast.Transaction;

import java.util.Optional;

class BlogService {
    private final BlogRepo blogRepo;

    BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    Blog updateBlog(Blog blog) throws FailedException {
        return blogRepo
                .transactional(txn -> {
                    try {
                        if (blogRepo.delete(blog) &&
                                blogRepo.save(blog)) {
                            return commit(blog, txn);
                        } else {
                            return rollback(txn);
                        }
                    } catch (Throwable throwable) {
                        return rollback(txn);
                    }
                })
                .orElseThrow(() -> new FailedException("Failed to update blog."));
    }

    private Optional<Blog> commit(Blog blog, Transaction txn) {
        txn.commit();
        return blogRepo.findById(blog.getId());
    }

    private Optional<Blog> rollback(Transaction txn) {
        txn.rollback();
        return Optional.empty();
    }
}
