package pl.upaid.domain.model.loan;

import lombok.extern.slf4j.Slf4j;
import pl.upaid.domain.model.Sleeper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.upaid.domain.model.loan.LoanerResponse.OK;

@Slf4j
public class Bik implements Loaner {

    public LoanerResponse askForALoan(String client) {
        log.info("BIK checking...");
        Sleeper.sleep(3, SECONDS);
        return OK;
    }
}
