package pl.upaid.domain.model.structure.ifcode

import pl.upaid.domain.model.structure.Client
import pl.upaid.domain.model.structure.Discount
import spock.lang.Specification

class PromotionGiverSpec extends Specification {

    private PromotionGiver promotionGiver = new PromotionGiver()

    def "should get 30% discount when client is younger than 10"() {
        given:
        Client youngClient = Client.of("Jan", 9)

        expect:
        promotionGiver.giveADiscount(youngClient) == Discount.of(0.30)
    }

    def "should get 10% discount when client is between 10 and 50"() {
        expect:
        promotionGiver.giveADiscount(client) == Discount.of(0.10)

        where:
        client << [Client.of("Jan", 10), Client.of("Jan", 25), Client.of("Jan", 50)]
    }

    def "should get 50% discount when client is older than 50"() {
        given:
        Client youngClient = Client.of("Jan", 51)

        expect:
        promotionGiver.giveADiscount(youngClient) == Discount.of(0.50)
    }

    //    ****************************************************************

    def "should get async 30% discount when client is younger than 10"() {
        given:
        Client youngClient = Client.of("Jan", 9)

        expect:
        promotionGiver.giveADiscountAsync(youngClient).blockingFirst() == Discount.of(0.30)
    }

    def "should get async 10% discount when client is between 10 and 50"() {
        expect:
        promotionGiver.giveADiscountAsync(client).blockingFirst() == Discount.of(0.10)

        where:
        client << [Client.of("Jan", 10), Client.of("Jan", 25), Client.of("Jan", 50)]
    }

//    ****************************************************************

    def "should get discount only for children"() {
        expect:
        promotionGiver.giveADiscountOnlyForChildren(client) == expectedDiscount

        where:
        client               | expectedDiscount
        Client.of("Jan", 17) | Discount.of(0.30)
        Client.of("Jan", 18) | Discount.of(0.0)
    }

    def "should get async discount only for children"() {
        expect:
        promotionGiver.giveADiscountOnlyForChildrenAsync(client).blockingFirst() == expectedDiscount

        where:
        client               | expectedDiscount
        Client.of("Jan", 17) | Discount.of(0.30)
        Client.of("Jan", 18) | Discount.of(0.0)
    }
}
