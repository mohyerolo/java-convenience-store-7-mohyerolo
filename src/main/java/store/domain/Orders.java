package store.domain;

import java.util.List;

public class Orders {
    private final List<Orders> orders;

    public Orders(final List<Orders> orders) {
        this.orders = orders;
    }
}
