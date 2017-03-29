package pl.upaid.domain.model;

import java.util.concurrent.TimeUnit;

public class Sleeper {

    public static void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            // Don't do that!
        }
    }
}
