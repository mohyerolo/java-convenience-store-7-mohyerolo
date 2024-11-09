package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.constant.PromoProductStatus;
import store.constant.PromotionType;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemTest {

    private Product product;

    @BeforeEach
    void setup() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TwoPlusOne,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        product = new Product("콜라", 1000, 10, promotion);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,PROMO_PRODUCT_REMAIN", "7,PROMO_PRODUCT_REMAIN", "9,PERFECT", "11,PROMO_PRODUCT_STOCK_OUT"
    })
    void 프로모션_진행후_주문상품이_남는지(int orderQuantity, String status) {
        OrderItem orderItem = new OrderItem("콜라", orderQuantity, product);
        int sets = orderItem.calcPromotionSets();
        PromoProductStatus quantityStatusAfterApplyPromotion = orderItem.getQuantityStatusAfterApplyPromotion(sets);
        assertEquals(status, quantityStatusAfterApplyPromotion.name());
    }
}
