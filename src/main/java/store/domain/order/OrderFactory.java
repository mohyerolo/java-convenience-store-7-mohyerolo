package store.domain.order;

import store.domain.Store;
import store.exception.CustomIllegalArgException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        for (String orderItemData : splitOrder) {
            OrderItem orderItem = OrderItemFactory.createOrderItem(orderItemData, store);
            addOrUpdateOrderItems(orderItems, orderItem);
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

    private static void addOrUpdateOrderItems(List<OrderItem> orderItems, OrderItem createdOrderItem) {
        Optional<OrderItem> alreadyExistingOrderItem = findAlreadyExistingOrderItem(orderItems, createdOrderItem.getOrderProductName());
        alreadyExistingOrderItem.ifPresentOrElse(
                firstReceivedOrderItem -> firstReceivedOrderItem.addDuplicatedOrderQuantity(createdOrderItem),
                () -> orderItems.add(createdOrderItem)
        );
    }

    private static Optional<OrderItem> findAlreadyExistingOrderItem(List<OrderItem> orderItems, String productName) {
        return orderItems.stream()
                .filter(orderItem -> orderItem.getOrderProductName().equals(productName))
                .findFirst();
    }

}
