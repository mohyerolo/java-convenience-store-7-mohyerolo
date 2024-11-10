package store.domain.promotion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.constant.PromotionType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PromotionTest {

    private Promotion promotion;

    @BeforeEach
    void setup() {
        promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
    }

    @Test
    void 프로모션_유효기간_지남() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 22));
        assertFalse(promotion.isAvailablePromotion());
    }

    @Test
    void 프로모션_유효함() {
        assertTrue(promotion.isAvailablePromotion());
    }

    @ParameterizedTest(name = "{2}개 재고 상품에서 {1}개를 시키면 {0}개에 2+1 프로모션이 적용됨")
    @CsvSource(value = {
            "9,10,10", "3,5,7", "3,6,3"
    })
    void 프로모션_적용되는_상품_개수(int promotionAppliedQuantity, int orderQuantity, int productStock) {
        Assertions.assertThat(promotion.calcPromotionQuantity(orderQuantity, productStock)).isEqualTo(promotionAppliedQuantity);
    }

    @ParameterizedTest(name = "{0}개 재고 상품에서 {1}개를 시키면 2+1 프로모션으로 {2}개를 무료로 받음")
    @CsvSource(value = {
            "10,9,3", "5,4,1", "4,7,1"
    })
    void 프로모션_적용돼서_무료로_받는_제품_개수(int productStock, int orderQuantity, int free) {
        Assertions.assertThat(promotion.calcPromoFreeQuantity(orderQuantity, productStock)).isEqualTo(free);
    }
}
