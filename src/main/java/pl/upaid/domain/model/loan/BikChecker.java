package pl.upaid.domain.model.loan;

import pl.upaid.domain.model.Sleeper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.upaid.domain.model.loan.CheckerResponse.REJECTED;

public class BikChecker implements LoanChecker {

    public CheckerResponse check(String client) {
        Sleeper.sleep(4, SECONDS);
        return REJECTED;
    }
}
