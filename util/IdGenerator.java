package util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static int getAndIncrement() {
        return counter.getAndIncrement();
    }
}
