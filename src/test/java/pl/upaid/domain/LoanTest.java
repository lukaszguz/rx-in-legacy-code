package pl.upaid.domain;

import io.reactivex.Observable;
import javaslang.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.upaid.domain.model.loan.BankChecker;
import pl.upaid.domain.model.loan.BankOnlyRejectChecker;
import pl.upaid.domain.model.loan.BikChecker;
import pl.upaid.domain.model.loan.CheckerResponse;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;
import static pl.upaid.domain.model.loan.CheckerResponse.OK;

@Slf4j
public class LoanTest {

    private Predicate<CheckerResponse> isOK = Predicates.is(OK);
    private BankChecker bankChecker = new BankChecker();
    private BankOnlyRejectChecker bankOnlyRejectChecker = new BankOnlyRejectChecker();
    private BikChecker bikChecker = new BikChecker();
    private LocalTime start;
    private String client = "Jan Kowalski";

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
    public void should_check_available_loan_sync() {
        // when:

        // then:
        assertTrue(true);
    }

    @Test
    public void should_check_available_loan_when_is_ok_sync_observable() {
        // when:
        Observable<Boolean> bankResponse = bankChecker.rxCheck(client)
                .map(isOK::test);

        Observable<Boolean> bikResponse = bikChecker.rxCheck(client)
                .map(isOK::test);

        // then:
    }


    @Test
    public void should_check_available_loan_async_observable() {
        // when:
        Observable<Boolean> bankResponse = bankChecker.rxCheckAsync(client)
                .map(isOK::test)
                .doOnNext(event -> log.info("Event: {}", event))
                .doOnSubscribe(x -> log.info("Subscribe BANK"))
                .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<Boolean> bikResponse = bikChecker.rxCheckAsync(client)
                .map(isOK::test)
                .doOnNext(event -> log.info("Event: {}", event))
                .doOnSubscribe(x -> log.info("Subscribe BIK"))
                .doOnDispose(() -> log.info("Unsubscribe BIK"));

        // then:
        Observable.zip(bankResponse, bikResponse, (bank, bik) -> bank && bik)
                .blockingSubscribe(response -> log.info("Got: {}", response));
    }

    @Test
    public void should_check_available_loan_when_is_first_ok_async_observable() {
        // when:
        Observable<CheckerResponse> bankResponse = bankChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK"))
                .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<CheckerResponse> bankRejectResponse = bankOnlyRejectChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK R"))
                .doOnDispose(() -> log.info("Unsubscribe BANK R"));

        // then
    }

    @Test
    public void observable_are_always_lazy() {

    }
}
