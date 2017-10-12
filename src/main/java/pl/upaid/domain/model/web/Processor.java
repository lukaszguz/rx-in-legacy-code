package pl.upaid.domain.model.web;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.upaid.domain.model.Sleeper;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
class Processor {

    String doSomething() {
        log.info("blocking process");
        Sleeper.sleep(200, TimeUnit.MILLISECONDS);
        return "something";
    }

    Single<String> doSomethingWithRx() {
        return Single.just("something-rx")
                     .doOnSuccess(x -> log.info("non blocking process"))
                     .delay(200, TimeUnit.MILLISECONDS);
    }
}
