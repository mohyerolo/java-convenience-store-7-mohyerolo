package store.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.Store;
import store.service.StoreService;

class OrderTest {
    private final Store store;

    OrderTest() {
        StoreService storeService = new StoreService();
        store = storeService.makeConvenienceStore();
    }

    @ParameterizedTest
    @CsvSource(value = {"[콜라-1],[사이다-2]|2", "[초코바-1],[에너지바-1]|1", "[물-1],[정식도시락-1]|0"}, delimiter = '|')
    void 주문중에_프로모션_있는_주문아이템(String input, int expectedListSize) {
        Order order = OrderFactory.createOrder(input, store);
        Assertions.assertThat(order.checkOrderItemCanAppliedPromotion().size()).isEqualTo(expectedListSize);
    }


}
