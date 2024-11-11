package store.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileReaderUtilTest {
    private static final int PRODUCTS_FILE_ROW = 16;

    private static final String PRODUCTS_FILE = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE = "src/main/resources/promotions.md";

    private static final String MISSING_FILE = "notFound.txt";
    private static final int PROMOTION_FILE_ROW = 3;

    @Test
    void 읽을_파일이_없음() {
        assertThatThrownBy(() -> FileReaderUtil.readFile(MISSING_FILE)).isInstanceOf(IOException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {PRODUCTS_FILE, PROMOTIONS_FILE})
    void 파일_읽기_성공(final String fileName) throws IOException {
        assertThat(FileReaderUtil.readFile(fileName)).isNotEmpty();
    }

    @Test
    void products_파일_읽어서_list반환() throws IOException {
        assertThat(FileReaderUtil.readFile(PRODUCTS_FILE).size()).isEqualTo(PRODUCTS_FILE_ROW);
    }

    @Test
    void promotions_파일_읽어서_list반환() throws IOException {
        assertThat(FileReaderUtil.readFile(PROMOTIONS_FILE).size()).isEqualTo(PROMOTION_FILE_ROW);
    }
}
