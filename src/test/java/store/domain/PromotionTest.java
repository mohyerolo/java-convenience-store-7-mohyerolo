package store.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PromotionTest {
    private static final String PROMOTION_FILE_FIELD_ERROR = "프로모션 정보 파일에 name,buy,get,start_date,end_date 양식을 지키지 못한 라인이 있습니다.";
    private static final String NOT_STRING_FIELD = "문자가 없는 칸이 존재합니다.";
    private static final String NOT_INT_FIELD = "숫자가 필요한 부분에 숫자가 아닌 문자가 들어가있습니다.";
    private static final String NOT_DATE_FIELD = "yyyy-MM-dd 날짜 형식이 필요한 부분에 다른 데이터가 들어가있습니다.";

    @Test
    void 프로모션_매개변수_필드_부족() {
        String[] input = new String[]{"name", "buy", "free", "startDate"};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Promotion(input))
                .withMessageContaining(PROMOTION_FILE_FIELD_ERROR);
    }

    @ParameterizedTest
    @MethodSource("provideBlankStrings")
    void 프로모션_필드_빈칸_존재(String[] input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Promotion(input))
                .withMessageContaining(NOT_STRING_FIELD);
    }

    @ParameterizedTest
    @MethodSource("provideNotInt")
    void 프로모션_필드_숫자_필요한곳에_아닌게_존재(String[] input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Promotion(input))
                .withMessageContaining(NOT_INT_FIELD);
    }

    @ParameterizedTest
    @MethodSource("provideNotDate")
    void 프로모션_필드_yyyy_MM_dd형식_필요한곳에_아닌게_존재(String[] input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Promotion(input))
                .withMessageContaining(NOT_DATE_FIELD);
    }

    private static Stream<Arguments> provideBlankStrings() {
        return Stream.of(
                Arguments.of((Object) new String[]{"", "1", "1", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{" ", "1", "1", "2024-01-01", "2024-12-31"})
        );
    }

    private static Stream<Arguments> provideNotInt() {
        return Stream.of(
                Arguments.of((Object) new String[]{"탄산2+1", "", "1", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", " ", "1", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", " ", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "a", "1", "2024-01-01", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "a", "2024-01-01", "2024-12-31"})
        );
    }

    private static Stream<Arguments> provideNotDate() {
        return Stream.of(
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "2024-01-01", ""}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "1", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "2024-01-01", "1"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "2024-01-1", "2024-12-31"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "2024-01-01", "2024-12-8"}),
                Arguments.of((Object) new String[]{"탄산2+1", "1", "1", "2024-15-60", "2024-12-8"})
        );
    }

}
