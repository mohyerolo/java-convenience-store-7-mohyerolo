package store.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.order.OrderItem;
import store.domain.order.OrderItemFactory;
import store.service.ProductService;
import store.service.StoreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderItemFactoryTest {

    private static final String TYPE_ERROR = "형식";
    private static final String NON_EXIST = "존재";
    private static final String EXCEEDED_STOCK = "초과";

    private final StoreService storeService = new StoreService(new ProductService());
    private final Store store = storeService.makeConvenienceStore();

    @Test
    void 주문_하나_생성_성공() {
        assertThat(OrderItemFactory.createOrderItem("사이다-10", store))
                .isInstanceOf(OrderItem.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10", "사이다", " ", "", "사디아-10-a",
            "-10", "사이다", "사이다-", " -10", "사이다- ",
            "사이다-a"
    })
    void 주문_상품_형식_에러(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemFactory.createOrderItem(input, store))
                .withMessageContaining(TYPE_ERROR);

    }

    @Test
    void 없는_상품_주문() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemFactory.createOrderItem("스모어초콜릿-1", store))
                .withMessageContaining(NON_EXIST);
    }

    @Test
    void 재고_초과() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemFactory.createOrderItem("사이다-100", store))
                .withMessageContaining(EXCEEDED_STOCK);
    }
}
