package pl.upaid.domain.model.loan;

import lombok.extern.slf4j.Slf4j;
import pl.upaid.domain.model.Sleeper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.upaid.domain.model.loan.CheckerResponse.REJECTED;

@Slf4j
public class BankOnlyRejectChecker implements LoanChecker {

    public CheckerResponse check(String client) {
        log.info("Bank which rejecting...");
        Sleeper.sleep(10, SECONDS);
        return REJECTED;
    }
}
