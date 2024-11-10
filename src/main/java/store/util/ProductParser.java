package store.util;

public class ProductParser {
    private static final String DELIMITER = ",";

    public static String[] parseFieldData(String productData) {
        return productData.split(DELIMITER);
    }

    public static int parseProductNumData(String numData) {
        return Integer.parseInt(numData);
    }
}
