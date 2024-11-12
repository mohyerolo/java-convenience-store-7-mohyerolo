package store.domain.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductFactoryTest {
    private static Promotions promotions;

    @BeforeAll
    static void setup() {
        List<String> promotionData = List.of("탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31", "반짝할인,1,1,2024-11-01,2024-11-30");
        promotions = Promotions.from(promotionData);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ",1000,1,반짝할인",
            "사이다,,1,반짝할인",
            "사이다,1000,,반짝할인"
    })
    void 상품생성_매개변수_문자열에_빈값(final String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ProductFactory.createProduct(input, promotions));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "사이다,1000,1,,"
    })
    void 상품생성_매개변수_문자열_끝에_빈값(final String input) {
        assertThatIllegalStateException()
                .isThrownBy(() -> ProductFactory.createProduct(input, promotions));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "사이다,a,1,반짝할인",
            "사이다,1000,1a,반짝할인"
    })
    void 상품_가격과_수량에_숫자가_아님(final String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ProductFactory.createProduct(input, promotions));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "콜라,1000,10,탄산2+1",
            "콜라,1000,10,null",
            "사이다,1000,5,반짝할인"
    })
    void 상품_생성_성공(final String input) {
        assertThatNoException()
                .isThrownBy(() -> ProductFactory.createProduct(input, promotions));
    }

    @Test
    void 상품_프로모션_적용_성공() {
        Product product = ProductFactory.createProduct("콜라,1000,10,탄산2+1", promotions);
        Promotion promotion = product.getPromotion();
        assertThat(promotion.getPromotionFreeProduct()).isEqualTo(1);
    }

}
