package store.service;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderFactory;
import store.domain.order.OrderItem;

import java.util.List;

public class OrderService {

    public Order createOrder(final String order, final Store store) {
        return OrderFactory.createOrder(order, store);
    }

    public List<OrderItem> checkPromotionExistingOrderItem(final Order order) {
        return order.checkOrderItemHavingPromotion();
    }

    public boolean isOrderItemHavingPromoQuantityBiggerThanPromoMet(final OrderItem orderItem) {
        return orderItem.isOrderItemHavingPromoQuantityBiggerThanPromoMet();
    }

    public int calcRemainQuantityAfterPromotionApply(final OrderItem orderItem) {
        return orderItem.calcRemainQuantityAfterPromotionApply();
    }

    public boolean isRemainQuantityMetPromoBuyNeed(final OrderItem orderItem, final int remainQuantity) {
        return orderItem.isRemainQuantityMetPromoBuyNeed(remainQuantity);
    }

    public boolean isRemainingQuantityAvailableInPromoStock(final OrderItem orderItem, final int remainQuantity) {
        return orderItem.isRemainingQuantityAvailableInPromoStock(remainQuantity);
    }

    public int getOrderItemPromotionFreeQuantity(final OrderItem orderItem) {
        return orderItem.getFreeProductQuantity();
    }

    public void buyPromoFreeProduct(final OrderItem orderItem) {
        orderItem.buyMorePromoProduct();
    }

    public void cancelOrderItemAsQuantity(final OrderItem orderItem, final int cancelQuantity) {
        orderItem.cancelOrder(cancelQuantity);
    }

    public boolean checkOrderStillAvailable(final Order order) {
        return order.checkOrderItemStillExists();
    }

}
