package pl.upaid.domain.model.loan;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public interface LoanChecker {

    CheckerResponse check(String client);

    default Observable<CheckerResponse> rxCheck(String client) {
        return Observable.fromCallable(() -> check(client));
    }

    default Observable<CheckerResponse> rxCheckAsync(String client) {
        return rxCheck(client)
                .subscribeOn(Schedulers.io());
    }
}
