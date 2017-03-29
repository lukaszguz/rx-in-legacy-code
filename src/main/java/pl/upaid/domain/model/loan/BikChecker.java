package pl.upaid.domain.model.loan;

import lombok.extern.slf4j.Slf4j;
import pl.upaid.domain.model.Sleeper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.upaid.domain.model.loan.CheckerResponse.REJECTED;

@Slf4j
public class BikChecker implements LoanChecker {

    public CheckerResponse check(String client) {
        log.info("BIK checking...");
        Sleeper.sleep(2, SECONDS);
        return REJECTED;
    }
}
