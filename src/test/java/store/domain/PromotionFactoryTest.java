package store.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.promotion.PromotionFactory;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PromotionFactoryTest {
    private static final String PROMOTION_FILE_FIELD_ERROR = "프로모션 정보 파일에 name,buy,get,start_date,end_date 양식을 지키지 못한 라인이 있습니다.";
    private static final String NOT_STRING_FIELD = "문자가 없는 칸이 존재합니다.";
    private static final String NOT_INT_FIELD = "숫자가 필요한 부분에 숫자가 아닌 문자가 들어가있습니다.";
    private static final String NOT_DATE_FIELD = "yyyy-MM-dd 날짜 형식이 필요한 부분에 다른 데이터가 들어가있습니다.";

    @Test
    void 프로모션_매개변수_필드_부족() {
        String input = "name,buy,get,start_date";
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PromotionFactory.createPromotion(input))
                .withMessageContaining(PROMOTION_FILE_FIELD_ERROR);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ",1,1,2024-01-01,2024-12-31",
            " ,1,1,2024-01-01,2024-12-31"
    })
    void 프로모션_필드_빈칸_존재(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PromotionFactory.createPromotion(input))
                .withMessageContaining(NOT_STRING_FIELD);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "탄산2+1,,1,2024-01-01,2024-12-31",
            "탄산2+1,1,,2024-01-01,2024-12-31",
            "탄산2+1,a,1,2024-01-01,2024-12-31"

    })
    void 프로모션_필드_숫자_필요한곳에_아닌게_존재(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PromotionFactory.createPromotion(input))
                .withMessageContaining(NOT_INT_FIELD);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "탄산2+1,1,1, ,2024-12-31",
            "탄산2+1,1,1,2024-01-01,1",
            "탄산2+1,1,1,2024-15-60,2024-12-8"
    })
    void 프로모션_필드_yyyy_MM_dd형식_필요한곳에_아닌게_존재(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PromotionFactory.createPromotion(input))
                .withMessageContaining(NOT_DATE_FIELD);
    }

}
