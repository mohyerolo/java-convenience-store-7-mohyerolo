package store.domain.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> orders;

    public Order(final List<OrderItem> orders) {
        this.orders = orders;
    }

    public List<OrderItem> findOrderItemHavingPromotion() {
        List<OrderItem> promotionAvailableOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orders) {
            if (orderItem.isOrderProductHavePromotion()) {
                promotionAvailableOrderItems.add(orderItem);
            }
        }
        return promotionAvailableOrderItems;
    }

    public void removeNonExistentOrderItem() {
        orders.removeIf(orderItem -> !orderItem.isOrderItemExists());
    }

    public boolean isStillOrderExists() {
        return !orders.isEmpty();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }
}
