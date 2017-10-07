package pl.upaid.domain.model.loan;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public interface Loaner {

    LoanerResponse askForALoan(String client);

    default Observable<LoanerResponse> askForALoanRx(String client) {
        return Observable.fromCallable(() -> askForALoan(client));
    }

    default Observable<LoanerResponse> askForALoanAsync(String client) {
        return askForALoanRx(client)
                .subscribeOn(Schedulers.io());
    }
}
