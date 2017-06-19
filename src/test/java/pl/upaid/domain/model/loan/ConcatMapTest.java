package pl.upaid.domain.model.loan;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.upaid.domain.model.Sleeper;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@Slf4j
public class ConcatMapTest {

    private LocalTime start;
    private Executor executor = Executors.newFixedThreadPool(2);
    private Scheduler scheduler = Schedulers.from(executor);
    private Random random = new Random(0);

    @Before
    public void setUp() throws Exception {
        log.info("Start check available loan");
        start = LocalTime.now();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Duration: {} ms", Duration.between(start, LocalTime.now()).toMillis());
    }

    @Test
    public void concatMapTest() {
        // given:
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        AtomicInteger counter = new AtomicInteger(0);

        Observable.fromIterable(strings)
                .map(s -> Tuple.of(counter.incrementAndGet(), s))
                .flatMap(this::longMethod)
                .doOnNext(s -> log.info("End process: {}", s))
                .toSortedList()
                .subscribe((list) -> log.info("End Duration: {} ms \nlist: {}", Duration.between(start, LocalTime.now()).toMillis(),list));

        Sleeper.sleep(4500, TimeUnit.MILLISECONDS);
    }


    private Observable<Tuple2<Integer, String>> longMethod(Tuple2<Integer, String> in) {
        return Observable.just(in)
                .doOnNext(s -> log.info("Long process: {}", s))
                .delay(random.nextInt(2000), TimeUnit.MILLISECONDS)
                .map(tuple -> tuple.map2(s -> "Long " + s))
                .observeOn(scheduler)
                ;
    }


}