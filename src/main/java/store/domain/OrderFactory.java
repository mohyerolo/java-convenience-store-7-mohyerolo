package store.domain;

import store.exception.CustomIllegalArgException;
import store.validator.DataTypeValidator;

public class OrderFactory {
    private static final String DELIMITER = "-";
    private static final int orderFieldSize = 2;
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";
    private static final String NON_EXIST_PRODUCT = "존재하지 않는 상품입니다.";
    private static final String EXCEEDED_STOCK = "재고 수량을 초과하여 구매할 수 없습니다.";

    public static Order takeOrder(String orderData, Store store) {
        String[] orderFields = splitAndValidateOrder(orderData, store);
        return new Order(orderFields[0], Integer.parseInt(orderFields[1]));
    }

    private static String[] splitAndValidateOrder(String orderData, Store store) {
        String[] orderFields = orderData.split(DELIMITER);
        validateOrder(orderFields, store);
        return orderFields;
    }

    private static void validateOrder(String[] orderFields, Store store) {
        if (orderFields.length != orderFieldSize) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
        DataTypeValidator.validateString(orderFields[0]);
        validateOrderProductExist(orderFields[0], store);

        DataTypeValidator.validateInt(orderFields[1]);
        validateOrderQuantity(orderFields[0], orderFields[1], store);
    }

    private static void validateOrderProductExist(String orderProductName, Store store) {
        if (!store.isStoreHaveProduct(orderProductName)) {
            throw new CustomIllegalArgException(NON_EXIST_PRODUCT);
        }
    }

    private static void validateOrderQuantity(String productName, String quantity, Store store) {
        if (!store.isStockOk(productName, Integer.parseInt(quantity))) {
            throw new CustomIllegalArgException(EXCEEDED_STOCK);
        }
    }
}
