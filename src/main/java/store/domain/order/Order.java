package store.domain.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> orders;

    public Order(final List<OrderItem> orders) {
        this.orders = orders;
    }

    public List<OrderItem> checkOrderItemHavingPromotion() {
        List<OrderItem> promotionAvailableOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orders) {
            if (orderItem.isOrderProductHavePromotion()) {
                promotionAvailableOrderItems.add(orderItem);
            }
        }
        return promotionAvailableOrderItems;
    }

    public boolean checkOrderItemStillExists() {
        for (OrderItem orderItem : orders) {
            if (orderItem.isOrderItemExists()) {
                return true;
            }
        }
        return false;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }
}
