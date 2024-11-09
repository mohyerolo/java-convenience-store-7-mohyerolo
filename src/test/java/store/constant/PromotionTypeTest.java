package store.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static store.constant.PromotionType.ONE_PLUS_ONE;
import static store.constant.PromotionType.TWO_PLUS_ONE;

class PromotionTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "4,10,4", "7,10,6","12,10,10"
    })
    void ONE_PLUS_ONE_할인_적용_세트(int orderQuantity, int productStock, int result) {
        assertThat(ONE_PLUS_ONE.calcPromoQuantity(orderQuantity, productStock)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,10,3", "6,10,6","12,10,9"
    })
    void TWO_PLUS_ONE_할인_적용_세트(int orderQuantity, int productStock, int result) {
        assertThat(TWO_PLUS_ONE.calcPromoQuantity(orderQuantity, productStock)).isEqualTo(result);
    }

}
