package pl.upaid.domain.model.loan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class BankController {

    private LoanChecker bankChecker = new BankChecker();

    @GetMapping("/bank/{client}")
    DeferredResult<CheckerResponse> chekClient(@PathVariable("client") String client) {
        DeferredResult<CheckerResponse> result = new DeferredResult<>();
        bankChecker.rxCheck(client)
                .subscribe(result::setResult);

        return result;
    }
}
