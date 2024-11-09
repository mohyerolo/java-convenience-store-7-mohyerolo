package store.service;

import store.constant.PromoProductStatus;
import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderAppliedPromotion;
import store.domain.order.OrderFactory;
import store.domain.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public Order takeOrder(String order, Store store) {
        return OrderFactory.createOrder(order, store);
    }

    public List<OrderItem> checkPromotionApplied(Order order) {
        return order.checkOrderItemCanAppliedPromotion();
    }

    public List<OrderAppliedPromotion> applyPromotionsToOrders(List<OrderItem> promotionExistOrderItems) {
        List<OrderAppliedPromotion> orderAppliedPromotions = new ArrayList<>();
        for (OrderItem orderItem : promotionExistOrderItems) {
            orderAppliedPromotions.add(createOrderAppliedPromotion(orderItem));
        }
        return orderAppliedPromotions;
    }

    private OrderAppliedPromotion createOrderAppliedPromotion(OrderItem orderItem) {
        int promotionSets = orderItem.calcPromotionSets();
        PromoProductStatus orderProductPromoStatus = orderItem.getQuantityStatusAfterApplyPromotion(promotionSets);
        return new OrderAppliedPromotion(orderItem, promotionSets, orderProductPromoStatus);
    }

}
