package store.constant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static store.constant.PromotionType.OnePlusOne;
import static store.constant.PromotionType.TwoPlusOne;

class PromotionTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "7,6,3", "10,7,3", "6,6,3", "7,10,3",
            "3,4,1"
    })
    void ONE_PLUS_ONE_할인_적용_세트(int orderQuantity, int productStock, int result) {
        Assertions.assertThat(OnePlusOne.calcPromoSets(orderQuantity, productStock)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,6,2", "7,6,2", "7,10,2",
            "7,5,1"
    })
    void TWO_PLUS_ONE_할인_적용_세트(int orderQuantity, int productStock, int result) {
        Assertions.assertThat(TwoPlusOne.calcPromoSets(orderQuantity, productStock)).isEqualTo(result);
    }

}
