package pl.upaid.domain;

import io.reactivex.Observable;
import javaslang.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.upaid.domain.model.loan.Bank;
import pl.upaid.domain.model.loan.BankOnlyReject;
import pl.upaid.domain.model.loan.Bik;
import pl.upaid.domain.model.loan.LoanerResponse;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;
import static pl.upaid.domain.model.loan.LoanerResponse.OK;

@Slf4j
public class LoanTest {

    private Predicate<LoanerResponse> isOK = Predicates.is(OK);
    private Bank bank = new Bank();
    private BankOnlyReject bankOnlyReject = new BankOnlyReject();
    private Bik bik = new Bik();
    private LocalTime start;
    private String client = "Jan Kowalski";

    @Before
    public void setUp() throws Exception {
        log.info("Start askForALoan available loan");
        start = LocalTime.now();
    }

    @After
    public void tearDown() throws Exception {
        log.info("Duration: {} ms", Duration.between(start, LocalTime.now())
                                            .toMillis());
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

        // then:
    }

    @Test
    public void should_check_available_loan_async_observable() {
        // when:
        Observable<Boolean> bankDecision = bank.askForALoanAsync(client)
                                               .map(isOK::test)
                                               .doOnNext(event -> log.info("Bank event: {}", event))
                                               .doOnSubscribe(x -> log.info("Subscribe BANK"))
                                               .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<Boolean> bikDecision = bik.askForALoanAsync(client)
                                             .map(isOK::test)
                                             .doOnNext(event -> log.info("Bik event: {}", event))
                                             .doOnSubscribe(x -> log.info("Subscribe BIK"))
                                             .doOnDispose(() -> log.info("Unsubscribe BIK"));

        // then:
        Observable.zip(bankDecision, bikDecision, (bank, bik) -> bank && bik)
                  .blockingSubscribe(response -> log.info("Got: {}", response));
    }

    @Test
    public void should_check_available_loan_when_is_first_ok_async_observable() {
        // when:
        Observable<LoanerResponse> bankDecision = bank.askForALoanAsync(client)
                                                      .doOnNext(event -> log.info("Bank event: {}", event))
                                                      .doOnSubscribe(x -> log.info("Subscribe BANK"))
                                                      .doOnDispose(() -> log.info("Unsubscribe BANK"));

        Observable<LoanerResponse> bankRejectDecision = bankOnlyReject.askForALoanAsync(client)
                                                                      .doOnNext(event -> log.info("Bank R event: {}", event))
                                                                      .doOnSubscribe(x -> log.info("Subscribe BANK R"))
                                                                      .doOnDispose(() -> log.info("Unsubscribe BANK R"));

        // then
    }

    @Test
    public void observable_are_always_lazy() {

    }
}
