package store.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ProductFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = {
            ",1000,1,반짝할인",
            "사이다,1000,1,",
            "사이다,,1,반짝할인",
            "사이다,1000,,반짝할인"
    })
    void 상품생성_매개변수_문자열예_빈값(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ProductFactory.createProduct(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "사이다,a,1,반짝할인",
            "사이다,1000,1a,반짝할인"
    })
    void 상품_가격과_수량에_숫자가_아님(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ProductFactory.createProduct(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "콜라,1000,10,탄산2+1",
            "콜라,1000,10,null",
            "사이다,1000,5,반짝할인"
    })
    void 상품_생성_성공(String input) {
        assertThatNoException()
                .isThrownBy(() -> ProductFactory.createProduct(input));
    }

}
