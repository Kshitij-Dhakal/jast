package dhaka.jast;

interface CheckedConsumer<T, E extends Throwable> {
    void consume(T value) throws E;
}
