package store.service;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderFactory;
import store.domain.order.OrderItem;

import java.util.List;

public class OrderService {

    public Order takeOrder(String order, Store store) {
        return OrderFactory.createOrder(order, store);
    }

    public List<OrderItem> checkPromotionApplied(Order order) {
        return order.checkOrderItemCanAppliedPromotion();
    }

    public void applyPromotionsToOrder(Order order, List<OrderItem> promotionExistOrderItems) {
        order.updateOrderStatus(promotionExistOrderItems);
    }

}
