package store.domain.product;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.util.ProductParser;
import store.validator.DataTypeValidator;
import store.validator.FileValidator;

public class ProductFactory {

    public static Product createProduct(final String productData, final Promotions promotions) {
        String[] productField = prepareProductFields(productData);

        int price = ProductParser.parseProductNumData(productField[1]);
        int quantity = ProductParser.parseProductNumData(productField[2]);
        Promotion promotion = promotions.findPromotion(productField[3]);

        return creatProductWithPromotion(productField[0], price, quantity, promotion);
    }

    private static String[] prepareProductFields(final String productData) {
        String[] productField = ProductParser.parseFieldData(productData);
        validateProduct(productField);
        return productField;
    }

    private static void validateProduct(final String[] productField) {
        FileValidator.validateProductField(productField);
        DataTypeValidator.validateString(productField[0]);
        DataTypeValidator.validateString(productField[3]);
        DataTypeValidator.validateInt(productField[1]);
        DataTypeValidator.validateInt(productField[2]);
    }

    private static Product creatProductWithPromotion(String name, int price, int quantity, Promotion promotion) {
        if (promotion == null) {
            return Product.of(name, price, quantity);
        }
        return Product.promotionOf(name, price, quantity, promotion);
    }

}
