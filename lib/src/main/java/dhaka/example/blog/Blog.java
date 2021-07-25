package dhaka.example.blog;

import java.util.List;

class Blog {
    private String id;
    private String content;
    private Long created;
    private Publisher publisher;
    private List<Comment> comments;

    private Blog(String id, String content, Long created, Publisher publisher, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.publisher = publisher;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public Builder toBuilder(){
        return newBuilder()
                .setId(id)
                .setContent(content)
                .setCreated(created)
                .setPublisher(publisher)
                .setComments(comments);
    }

    public static class Builder {
        private String id;
        private String content;
        private Long created;
        private Publisher publisher;
        private List<Comment> comments;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCreated(Long created) {
            this.created = created;
            return this;
        }

        public Builder setPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Blog build() {
            return new Blog(id, content, created, publisher, comments);
        }
    }
}
