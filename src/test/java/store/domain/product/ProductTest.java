package store.domain.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.constant.PromotionType;
import store.domain.promotion.Promotion;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Product product;

    @BeforeEach
    void setup() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        product = new Product("콜라", 1000, 10, promotion);
    }

    @Test
    void 상품_프로모션_없어서_프로모션_불가능() {
        Promotion promotion = new Promotion("null", PromotionType.NO_PROMO,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        Product product = new Product("물", 1000, 1, promotion);
        Assertions.assertFalse(product.isProductHavePromotion());
    }

    @Test
    void 상품_날짜떄문에_프로모션_불가능() {
        Promotion promotion = new Promotion("MD추천상품", PromotionType.ONE_PLUS_ONE,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 7, 22));
        Product product = new Product("물", 1000, 4, promotion);
        Assertions.assertFalse(product.isProductHavePromotion());
    }

    @Test
    void 상품_프로모션_가능() {
        Assertions.assertTrue(product.isProductHavePromotion());
    }

    @Test
    void 구매한만큼_상품_재고_감소() {
        product.reduceStock(3);
        assertThat(product.getQuantity()).isEqualTo(7);
    }

    @Test
    void 구매가_프로모션있는_제품의개수보다_많으면_남은값_리턴() {
        int remainOrderQuantity = product.reduceStock(11);
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(remainOrderQuantity).isEqualTo(1);
    }
}
