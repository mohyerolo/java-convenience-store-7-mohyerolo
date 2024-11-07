package store.domain;

import store.validator.DataTypeValidator;
import store.validator.FileValidator;

public class ProductFactory {
    private static final String DELIMITER = ",";

    public static Product createProduct(String productData) {
        String[] productField = splitAndValidateProduct(productData);
        int price = Integer.parseInt(productField[1]);
        int quantity = Integer.parseInt(productField[2]);
        return new Product(productField[0], price, quantity, productField[3]);
    }

    private static String[] splitAndValidateProduct(String productData) {
        String[] productField = productData.split(DELIMITER);
        validateProduct(productField);
        return productField;
    }

    private static void validateProduct(String[] productField) {
        FileValidator.validateProductField(productField);
        DataTypeValidator.validateString(productField[0]);
        DataTypeValidator.validateString(productField[3]);
        DataTypeValidator.validateInt(productField[1]);
        DataTypeValidator.validateInt(productField[2]);
    }
}
