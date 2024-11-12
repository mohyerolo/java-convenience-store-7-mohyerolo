package store.domain.order;

import store.domain.Store;
import store.exception.CustomIllegalArgException;
import store.util.OrderItemParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderFactory {

    private static final String DELIMITER = ",";
    private static final String ORDER_REG_EXP = "^\\[.+]$";
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";

    public static Order createOrder(final String orders, final Store store) {
        String[] splitOrder = parseOrders(orders);
        List<OrderItem> orderItems = makeEachOrderItem(splitOrder, store);
        return new Order(orderItems);
    }

    private static List<OrderItem> makeEachOrderItem(final String[] splitOrder, final Store store) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String orderItemData : splitOrder) {
            String[] orderFields = OrderItemParser.parseOrderItem(orderItemData, store);
            addOrUpdateOrderItems(orderItems, orderFields, store);
        }
        return orderItems;
    }

    private static String[] parseOrders(final String orders) {
        String[] splitOrder = orders.split(DELIMITER);
        validateEachOrder(splitOrder);
        return splitOrder;
    }

    private static void validateEachOrder(final String[] splitOrder) {
        if (Arrays.stream(splitOrder).anyMatch(order -> !order.matches(ORDER_REG_EXP))) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }

    private static void addOrUpdateOrderItems(final List<OrderItem> orderItems, final String[] orderItemFields, final Store store) {
        findAlreadyExistingOrderItem(orderItems, orderItemFields[0])
                .ifPresentOrElse(
                        orderItem -> orderItem.increaseQuantity(Integer.parseInt(orderItemFields[1])),
                        () -> orderItems.add(OrderItemFactory.createOrderItem(orderItemFields, store))
                );
    }

    private static Optional<OrderItem> findAlreadyExistingOrderItem(final List<OrderItem> orderItems, final String productName) {
        return orderItems.stream()
                .filter(orderItem -> orderItem.isNameDuplicated(productName))
                .findFirst();
    }

}
