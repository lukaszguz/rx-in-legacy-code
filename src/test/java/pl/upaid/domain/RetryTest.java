package pl.upaid.domain;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.upaid.domain.model.loan.BikChecker;
import pl.upaid.domain.model.loan.CheckerResponse;

import java.time.Duration;
import java.time.LocalTime;

import static java.util.concurrent.TimeUnit.*;
import static pl.upaid.domain.model.loan.CheckerResponse.OK;
import static pl.upaid.domain.model.loan.CheckerResponse.REJECTED;

@Slf4j
public class RetryTest {

    private LocalTime start;

    @Before
    public void setUp() throws Exception {
        log.info("Start test");
        start = LocalTime.now();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Duration: {} ms", Duration.between(start, LocalTime.now()).toMillis());
    }


    @Test
    public void should_retry_four_times() throws InterruptedException {
        // given:
        TestScheduler testScheduler = new TestScheduler();

        // when:
        TestObserver<CheckerResponse> testObserver = Observable.timer(10, MINUTES)
                .map(x -> OK)
                .timeout(1, SECONDS, testScheduler)
                .doOnError(e -> log.warn("Not working! " + e))
                .retry(3)
                .onErrorReturn(e -> REJECTED)
                .test();

        // then:
        testObserver
                .assertNoValues()
                .assertNoErrors();


        testScheduler.advanceTimeBy(3999, MILLISECONDS);
        testObserver
                .assertNoValues()
                .assertNoErrors();


        testScheduler.advanceTimeBy(1, MILLISECONDS);
        testObserver
                .assertNoErrors()
                .assertValue(REJECTED);
    }
}
