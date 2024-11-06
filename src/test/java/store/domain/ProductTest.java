package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ProductTest {

    @ParameterizedTest
    @MethodSource("provideBlankStrings")
    void 상품생성_매개변수_문자열예_빈값(String[] input) {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(input));
    }

    @ParameterizedTest
    @MethodSource("provideNotNumPriceAndQuantity")
    void 상품_가격과_수량에_숫자가_아님(String[] input) {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(input));
    }

    @ParameterizedTest
    @MethodSource("provideProducts")
    void 상품_생성_성공(String[] input) {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Product(input));
    }

    private static Stream<Arguments> provideBlankStrings() {
        return Stream.of(
                Arguments.of((Object) new String[]{"", "1000", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000", "1", ""}),
                Arguments.of((Object) new String[]{"사이다", "1000", "1", " "}),
                Arguments.of((Object) new String[]{" ", "1000", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000", "", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", " ", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000", " ", "반짝할인"})
        );
    }

    private static Stream<Arguments> provideNotNumPriceAndQuantity() {
        return Stream.of(
                Arguments.of((Object) new String[]{"사이다", "a", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000", "a", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000a", "1", "반짝할인"}),
                Arguments.of((Object) new String[]{"사이다", "1000", "1a", "반짝할인"})
        );
    }

    private static Stream<Arguments> provideProducts() {
        return Stream.of(
                Arguments.of((Object) new String[]{"콜라", "1000", "10", "탄산2+1"}),
                Arguments.of((Object) new String[]{"콜라", "1000", "10", "null"}),
                Arguments.of((Object) new String[]{"사이다", "1000", "5", "반짝할인"}),
                Arguments.of((Object) new String[]{"바삭한 감자칩", "1000", "5", "반짝할인"})
        );
    }
}
