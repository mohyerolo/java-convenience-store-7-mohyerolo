package store.service;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderFactory;

public class OrderService {

    public Order takeOrder(String order, Store store) {
        return OrderFactory.createOrder(order, store);
    }
}
