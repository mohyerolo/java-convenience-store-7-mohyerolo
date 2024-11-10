package store.util;

import store.domain.Store;
import store.exception.CustomIllegalArgException;
import store.validator.DataTypeValidator;

public class OrderItemParser {
    private static final String PREFIX_SUFFIX_REPLACE_REG_EXP = "^\\[|]$";
    private static final String DELIMITER = "-";
    private static final int orderFieldSize = 2;
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";
    private static final String NON_EXIST_PRODUCT = "존재하지 않는 상품입니다.";
    private static final String EXCEEDED_STOCK = "재고 수량을 초과하여 구매할 수 없습니다.";

    public static String[] parseOrderItem(final String orderData, final Store store) {
        String[] orderFields = removeSquareBrackets(orderData).split(DELIMITER);
        validateOrderDataType(orderFields);
        validateOrderProductExist(orderFields[0], store);
        validateOrderQuantity(orderFields[0], orderFields[1], store);
        return orderFields;
    }

    private static String removeSquareBrackets(final String orderData) {
        return orderData.replaceAll(PREFIX_SUFFIX_REPLACE_REG_EXP, "");
    }

    private static void validateOrderDataType(final String[] orderFields) {
        if (orderFields.length != orderFieldSize) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
        DataTypeValidator.validateString(orderFields[0]);
        DataTypeValidator.validateInt(orderFields[1]);
    }

    private static void validateOrderProductExist(final String orderProductName, final Store store) {
        if (!store.isStoreHaveProduct(orderProductName)) {
            throw new CustomIllegalArgException(NON_EXIST_PRODUCT);
        }
    }

    private static void validateOrderQuantity(final String productName, final String quantity, final Store store) {
        if (!store.isStockOk(productName, Integer.parseInt(quantity))) {
            throw new CustomIllegalArgException(EXCEEDED_STOCK);
        }
    }
}
