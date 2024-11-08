package store.domain;

import store.exception.CustomIllegalArgException;

import java.util.Arrays;

public class OrderFactory {

    private static final String DELIMITER = ",";
    private static final String ORDER_REG_EXP = "^\\[.+]$";
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";

    public static void takeOrder(String orders, Store store) {
        String[] splitOrders = splitOrders(orders);
        validateEachOrder(splitOrders);
    }

    private static String[] splitOrders(String orders) {
        return orders.split(DELIMITER);
    }

    private static void validateEachOrder(String[] splitOrders) {
        if (Arrays.stream(splitOrders).anyMatch(order -> !order.matches(ORDER_REG_EXP))) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }
}
