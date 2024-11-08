package store.domain;

import java.util.List;

public class Order {
    private final List<OrderItem> orders;

    public Order(final List<OrderItem> orders) {
        this.orders = orders;
    }
}
