package store.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.order.OrderFactory;
import store.service.StoreService;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderFactoryTest {
    private final Store store;

    OrderFactoryTest() {
        StoreService storeService = new StoreService();
        store = storeService.makeConvenienceStore();
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "사이다-1", "사이다-1,콜라-1",
            "-사이다-0]", "[사이다-1,콜라-1]"
    })
    void 주문서_형식_에러(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderFactory.createOrder(input, store));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-1]", "[사이다-1],[콜라-1]"})
    void 주문서_형식_정상적(String input) {
        assertThatNoException()
                .isThrownBy(() -> OrderFactory.createOrder(input, store));
    }

}
