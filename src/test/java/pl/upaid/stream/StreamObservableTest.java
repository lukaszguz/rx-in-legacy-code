package pl.upaid.stream;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StreamObservableTest {

    @Test
    public void should_concat_two_list() {
        Observable<String> first = Observable.just("A", "B", "C");
        Observable<String> second = Observable.just("d", "e", "f");

        first.concatWith(second)
                .blockingSubscribe(response -> log.info("Got: {}", response));
    }

    @Test
    public void should_concat_three_list() {
        Observable<String> first = Observable.just("A", "B", "C");
        Observable<String> second = Observable.just("d", "e", "f");
        Observable<String> third = Observable.just("G", "H", "I");

        first.concatWith(second)
                .concatWith(third)
                .blockingSubscribe(response -> log.info("Got: {}", response));

    }

    @Test
    public void should_zip_two_list() {
        Observable<String> first = Observable.just("A", "B", "C");
        Observable<String> second = Observable.just("d", "e", "f");

        first.zipWith(second, (s, s2) -> s + ":" + s2)
                .blockingSubscribe(response -> log.info("Got: {}", response));
    }
}