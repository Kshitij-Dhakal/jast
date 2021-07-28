package dhaka.example.blog;

class Publisher {
    private String id;
    private String name;

    public Publisher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PublisherBuilder newBuilder(){
        return new PublisherBuilder();
    }

    public static class PublisherBuilder {
        private String id;
        private String name;

        public PublisherBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public PublisherBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Publisher build() {
            return new Publisher(id, name);
        }
    }
}
