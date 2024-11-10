package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.constant.PromotionType;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    private Product product;

    @BeforeEach
    void setup() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        product = new Product("콜라", 1000, 10, promotion);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,true", "6,true", "7,false", "11,false"
    })
    void 프로모션_진행후_주문상품이_남는지(int orderQuantity, boolean allProductAppliedPromotion) {
        OrderItem orderItem = new OrderItem("콜라", orderQuantity, product);
        assertThat(orderItem.isPromotionWellApplied()).isEqualTo(allProductAppliedPromotion);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,1", "5,2", "3,0", "12,3", "17,8" // 프로모션이 되는 콜라는 10개밖에 없음
    })
    void 프로모션_진행후_남은_주문개수(int orderQuantity, int remainQuantity) {
        OrderItem orderItem = new OrderItem("콜라", orderQuantity, product);
        assertThat(orderItem.calcRemainQuantityAfterPromotionApply()).isEqualTo(remainQuantity);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,true", "5,true", "12,false", "17,false", // 프로모션이 되는 콜라는 10개밖에 없음
    })
    void 프로모션_진행후_남아있는_주문_프로모션_제품_안에서_구매가능한지(int orderQuantity, boolean isProductStockAvailable) {
        OrderItem orderItem = new OrderItem("콜라", orderQuantity, product);
        int remainQuantityAfterPromotionApply = orderItem.calcRemainQuantityAfterPromotionApply();
        assertThat(orderItem.isRemainQuantityCanAppliedPromotionProduct(remainQuantityAfterPromotionApply)).isEqualTo(isProductStockAvailable);
    }

}
