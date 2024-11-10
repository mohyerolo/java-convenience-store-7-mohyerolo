package store.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.order.OrderItem;
import store.domain.order.OrderItemFactory;
import store.service.StoreService;
import store.util.OrderItemParser;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemFactoryTest {
    private final Store store = new StoreService().makeConvenienceStore();

    @Test
    void 주문_하나_생성_성공() {
        assertThat(OrderItemFactory.createOrderItem(new String[]{"사이다", "10"}, store))
                .isInstanceOf(OrderItem.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"[사이다-10],true", "[감자칩-1],true", "[물-1],false"})
    void 주문_상품에_해당하는_프로모션이_들어감(String orderData, boolean promotion) {
        String[] orderFields = OrderItemParser.parseOrderItem(orderData, store);
        OrderItem orderItem = OrderItemFactory.createOrderItem(orderFields, store);
        assertThat(orderItem.isOrderProductHavePromotion()).isEqualTo(promotion);
    }
}
