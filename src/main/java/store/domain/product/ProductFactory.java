package store.domain.product;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.validator.DataTypeValidator;
import store.validator.FileValidator;

public class ProductFactory {
    private static final String DELIMITER = ",";

    public static Product createProduct(String productData, Promotions promotions) {
        String[] productField = splitAndValidateProduct(productData);
        int price = Integer.parseInt(productField[1]);
        int quantity = Integer.parseInt(productField[2]);
        Promotion promotion = findProductPromotion(productField[3], promotions);

        return new Product(productField[0], price, quantity, promotion);
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

    private static Promotion findProductPromotion(String productPromotion, Promotions promotions) {
        return promotions.findPromotion(productPromotion);
    }
}
