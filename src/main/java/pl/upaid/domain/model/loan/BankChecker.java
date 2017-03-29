package pl.upaid.domain.model.loan;

import pl.upaid.domain.model.Sleeper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.upaid.domain.model.loan.CheckerResponse.OK;

public class BankChecker implements LoanChecker {

    public CheckerResponse check(String client) {
        Sleeper.sleep(1, SECONDS);
        return OK;
    }
}
