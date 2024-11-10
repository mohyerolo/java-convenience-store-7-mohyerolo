package store.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Store;
import store.service.StoreService;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderItemParserTest {
    private static final String TYPE_ERROR = "형식";
    private static final String NON_EXIST = "존재";
    private static final String EXCEEDED_STOCK = "초과";

    private final StoreService storeService = new StoreService();
    private final Store store = storeService.makeConvenienceStore();

    @ParameterizedTest
    @ValueSource(strings = {
            "10", "사이다", " ", "", "사디아-10-a",
            "-10", "사이다", "사이다-", " -10", "사이다- ",
            "사이다-a"
    })
    void 주문_상품_형식_에러(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemParser.parseOrderItem(input, store))
                .withMessageContaining(TYPE_ERROR);

    }

    @Test
    void 없는_상품_주문() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemParser.parseOrderItem("스모어초콜릿-1", store))
                .withMessageContaining(NON_EXIST);
    }

    @Test
    void 재고_초과() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderItemParser.parseOrderItem("사이다-100", store))
                .withMessageContaining(EXCEEDED_STOCK);
    }


}
