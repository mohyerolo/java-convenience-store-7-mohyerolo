package store.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FileReaderUtilTest {
    private static final String FILE_NOT_FOUND = "[ERROR] 파일을 찾을 수 없습니다";
    private static final String PRODUCTS_FILE = "products.md";
    private static final int PRODUCTS_FILE_ROW = 16;

    private static final String PROMOTION_FILE = "promotions.md";

    private static final String MISSING_FILE = "notFound.txt";
    private static final int PROMOTION_FILE_ROW = 4;

    @Test
    void 읽을_파일이_없음() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FileReaderUtil.readFile(MISSING_FILE))
                .withMessage(FILE_NOT_FOUND);
    }


    @ParameterizedTest
    @ValueSource(strings = {PRODUCTS_FILE, PROMOTION_FILE})
    void 파일_읽기_성공(String fileName) {
        assertThat(FileReaderUtil.readFile(fileName)).isNotEmpty();
    }

    @Test
    void products_파일_읽어서_list반환() {
        assertThat(FileReaderUtil.readFile(PRODUCTS_FILE).size()).isEqualTo(PRODUCTS_FILE_ROW);
    }

    @Test
    void promotions_파일_읽어서_list반환() {
        assertThat(FileReaderUtil.readFile(PROMOTION_FILE).size()).isEqualTo(PROMOTION_FILE_ROW);
    }
}
