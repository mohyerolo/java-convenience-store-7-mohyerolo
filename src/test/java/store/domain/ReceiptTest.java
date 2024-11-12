package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.constant.PromotionType;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTest {
    private final Receipt receipt;

    ReceiptTest() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        Product cola = Product.promotionOf("콜라", 1000, 10, promotion);
        Product energy = Product.of("에너지바", 2000, 5);

        OrderItem orderItem = OrderItem.of("콜라", 3, cola);
        OrderItem orderItem2 = OrderItem.of("에너지바", 5, energy);

        Order order = new Order(List.of(orderItem, orderItem2));
        receipt = Receipt.from(order);
    }

    @Test
    void 전체_개수_계산() {
        assertThat(receipt.calcTotalQuantity()).isEqualTo(8);
    }

    @Test
    void 전체_금액_계산() {
        assertThat(receipt.calcTotalAmount()).isEqualTo(13000);
    }

    @Test
    void 멤버십_할인_받을떄_계산() {
        assertThat(receipt.calcMembershipDiscount(true)).isEqualTo(3000);
    }

    @Test
    void 멤버십_할인_안받을떄_계산() {
        assertThat(receipt.calcMembershipDiscount(false)).isEqualTo(0);
    }

    @Test
    void 프로모션_할인_계산() {
        assertThat(receipt.calcPromotionDiscount()).isEqualTo(1000);
    }

}
