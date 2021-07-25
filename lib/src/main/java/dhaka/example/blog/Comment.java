package dhaka.example.blog;

class Comment {
    private String id;
    private String content;
    private Long created;

    private Comment(String id, String content, Long created) {
        this.id = id;
        this.content = content;
        this.created = created;
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

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String content;
        private Long created;

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

        public Comment build() {
            return new Comment(id, content, created);
        }
    }
}
