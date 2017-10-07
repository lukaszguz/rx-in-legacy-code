package pl.upaid.domain.model.loan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class BankController {

    private Loaner bankChecker = new Bank();

    @GetMapping("/bank/{client}")
    DeferredResult<LoanerResponse> chekClient(@PathVariable("client") String client) {
        DeferredResult<LoanerResponse> result = new DeferredResult<>();
        bankChecker.askForALoanRx(client)
                .subscribe(result::setResult);

        return result;
    }
}
