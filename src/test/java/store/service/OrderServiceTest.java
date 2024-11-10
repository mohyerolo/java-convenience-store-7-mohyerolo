package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.constant.PromotionType;
import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();
    private Order orders;

    @BeforeEach
    void setup() {
        StoreService storeService = new StoreService();
        Store store = storeService.makeConvenienceStore();
        orders = orderService.createOrder("[콜라-5],[사이다-2],[물-1]", store);
    }

    @Test
    void 주문중에_행사_적용되는_제품만_가져오기() {
        assertThat(orderService.checkPromotionApplied(orders).size()).isEqualTo(2);
    }

}
