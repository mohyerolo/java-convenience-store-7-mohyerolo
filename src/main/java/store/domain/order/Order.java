package store.domain.order;

import store.domain.Store;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> orders;

    public Order(final List<OrderItem> orders) {
        this.orders = orders;
    }

    public List<OrderItem> checkOrderItemCanAppliedPromotion() {
        List<OrderItem> promotionAvailableOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orders) {
            if (orderItem.isOrderProductHavePromotion()) {
                promotionAvailableOrderItems.add(orderItem);
            }
        }
        return promotionAvailableOrderItems;
    }

}
