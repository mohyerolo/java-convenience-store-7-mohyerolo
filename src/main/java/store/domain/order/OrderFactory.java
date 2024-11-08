package store.domain.order;

import store.domain.Store;
import store.exception.CustomIllegalArgException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderFactory {

    private static final String DELIMITER = ",";
    private static final String ORDER_REG_EXP = "^\\[.+]$";
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";

    public static Order createOrder(String orders, Store store) {
        String[] splitOrder = splitOrders(orders);
        validateEachOrder(splitOrder);

        List<OrderItem> orderItems = makeEachOrderItem(splitOrder, store);
        return new Order(orderItems);
    }

    private static List<OrderItem> makeEachOrderItem(String[] splitOrder, Store store) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String orderItem : splitOrder) {
            orderItems.add(OrderItemFactory.createOrderItem(orderItem, store));
        }
        return orderItems;
    }

    private static String[] splitOrders(String orders) {
        return orders.split(DELIMITER);
    }

    private static void validateEachOrder(String[] splitOrder) {
        if (Arrays.stream(splitOrder).anyMatch(order -> !order.matches(ORDER_REG_EXP))) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }
}
