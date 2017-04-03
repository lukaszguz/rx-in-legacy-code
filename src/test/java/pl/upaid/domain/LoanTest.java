package pl.upaid.domain;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.upaid.domain.model.loan.CheckerResponse.OK;

@Slf4j
public class LoanTest {

    private Predicate<CheckerResponse> isOK = Predicates.is(OK);
    private BankChecker bankChecker = new BankChecker();
    private BikChecker bikChecker = new BikChecker();
    private BankOnlyRejectChecker bankOnlyRejectChecker = new BankOnlyRejectChecker();
    private LocalTime start;
    private String client = "Jan Kowalski";

    @Before
    public void setUp() throws Exception {
        log.info("Start check available loan");
        start = LocalTime.now();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Duration: {}", Duration.between(start, LocalTime.now()).toMillis());
    }

    @Test
    public void should_check_available_loan_sync() {
        // when:

        // then:
        assertTrue(true);
    }

    @Test
    public void should_check_available_loan_sync_observable() {
        // when:
        Observable<CheckerResponse> bankResult = bankChecker.rxCheck(client); // fast
        Observable<CheckerResponse> bikResult = bikChecker.rxCheck(client); // slow

        // then:

    }

    @Test
    public void should_check_available_loan_when_is_ok_sync_observable() {
        // when:
        Observable<CheckerResponse> bankResponse = bankChecker.rxCheck(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK"))
                .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<CheckerResponse> bikResponse = bikChecker.rxCheck(client)
                .doOnSubscribe(x -> log.info("Subscribe BIK"))
                .doOnDispose(() -> log.info("Unsubscribe BIK"));

    }


    @Test
    public void should_check_available_loan_async_observable() {
        // when:
        Observable<CheckerResponse> bankResponse = bankChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK"))
                .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<CheckerResponse> bikResponse = bikChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BIK"))
                .doOnDispose(() -> log.info("Unsubscribe BIK"));

    }

    @Test
    public void should_check_available_loan_when_is_first_ok_async_observable() {
        // when:
        Observable<CheckerResponse> bankResponse = bankChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK"))
                .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<CheckerResponse> bikResponse = bikChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BIK"))
                .doOnDispose(() -> log.info("Unsubscribe BIK"));

        Observable<CheckerResponse> rejectResponse = bankOnlyRejectChecker.rxCheckAsync(client)
                .doOnSubscribe(x -> log.info("Subscribe BANK R"))
                .doOnDispose(() -> log.info("Unsubscribe BANK R"));

    }

    @Test
    public void observable_are_always_lazy() {

    }
}
