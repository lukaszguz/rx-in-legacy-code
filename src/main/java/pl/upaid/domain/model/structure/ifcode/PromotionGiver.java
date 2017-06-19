package pl.upaid.domain.model.structure.ifcode;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import pl.upaid.domain.model.structure.Client;
import pl.upaid.domain.model.structure.Discount;

import static io.vavr.API.*;

@Slf4j
class PromotionGiver {

    Discount giveADiscount(Client client) {
        if (client.getAge() < 10) {
            return Discount.of(0.30);
        } else if (client.getAge() >= 10 && client.getAge() <= 50)
            return Discount.of(0.10);
        else {
            return Discount.of(0.50);
        }
    }

    Observable<Discount> giveADiscountAsync(Client client) {
        return Observable.just(client)
                .map(Client::getAge)
                .map(clientAge ->
                        Match(clientAge)
                                .of(
                                        Case($(age -> age < 10), Discount.of(0.30)),
                                        Case($(age -> age >= 10 && age <= 50), Discount.of(0.10)),
                                        Case($(), Discount.of(0.50))
                                )
                );
    }


    Discount giveADiscountOnlyForChildren(Client client) {
        if (client.getAge() < 18) {
            return Discount.of(0.30);
        } else {
            return Discount.of(0.0);
        }
    }


    Observable<Discount> giveADiscountOnlyForChildrenAsync(Client client) {
        return Observable.just(client)
                .map(Client::getAge)
                .filter(age -> age < 18)
                .map(x -> Discount.of(0.30))
                .switchIfEmpty(Observable.defer(() -> Observable.just(client)
                        .doOnNext(adult -> log.info("Adult: {}", adult))
                        .map(adult -> Discount.of(0.0))
                ));
    }
}
