package dhaka.jast;

import java.util.function.Consumer;

class Unchecked {
    private Unchecked() {
        //no instance
    }

    public static <T> T throwChecked(Throwable throwable) {
        org.jooq.lambda.Unchecked.throwChecked(throwable);
        return null;
    }

    public static <T, E extends Throwable> Consumer<T> consume(CheckedConsumer<T, E> consumer) {
        return org.jooq.lambda.Unchecked.consumer(consumer::consume);
    }
}
