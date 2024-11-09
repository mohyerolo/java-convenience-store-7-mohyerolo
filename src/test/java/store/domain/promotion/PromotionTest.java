package store.domain.promotion;

import org.junit.jupiter.api.Test;
import store.constant.PromotionType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void 프로모션_유효기간_지남() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 22));
        assertFalse(promotion.isAvailablePromotion());
    }

    @Test
    void 프로모션_유효함() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));
        assertTrue(promotion.isAvailablePromotion());
    }

    @Test
    void 프로모션_진행되는_상품개수() {
        Promotion promotion = new Promotion("탄산2+1", PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 22));

    }
}
