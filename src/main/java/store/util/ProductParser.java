package store.util;

public class ProductParser {
    private static final String DELIMITER = ",";

    public static String[] parseFieldData(final String productData) {
        return productData.split(DELIMITER);
    }

    public static int parseProductNumData(final String numData) {
        return Integer.parseInt(numData);
    }
}
