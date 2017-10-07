package pl.upaid.domain.model.web;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.upaid.domain.model.Sleeper;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
class Processor {

    String doSomethingWithDelay() {
        log.info("Start process with delay");
        Sleeper.sleep(200, TimeUnit.MILLISECONDS);
        log.info("End process with delay");
        return "something";
    }

    Single<String> doSomethingWithDelayRx() {
        return Single.just("something")
                     .doOnSuccess(x -> log.info("Start process with delay"))
                     .delay(200, TimeUnit.MILLISECONDS)
                     .doOnSuccess(x -> log.info("End process with delay"));
    }
}
