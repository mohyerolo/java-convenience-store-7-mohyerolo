package store.controller;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.service.OrderService;
import store.validator.DataTypeValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class OrderController {
    private static final String ANSWER_YES = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public OrderController(final InputView inputView, final OutputView outputView, final OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public Order takeOrder(final Store store) {
        Order order = createOrder(store);
        checkPromotion(order);
        orderService.removeNonExistentOrderItem(order);
        return order;
    }

    public boolean checkOrderStillExists(final Order order) {
        return orderService.checkOrderStillAvailable(order);
    }

    private Order createOrder(final Store store) {
        return executeWithRetry(() -> {
            String orders = inputView.readBuyProductAndQuantity();
            return orderService.createOrder(orders, store);
        });
    }

    private void checkPromotion(final Order order) {
        List<OrderItem> promotionExistOrderItems = orderService.checkPromotionExistingOrderItem(order);
        readCustomersPromotionStatusOpinion(promotionExistOrderItems);
    }

    private void readCustomersPromotionStatusOpinion(final List<OrderItem> orderExistingPromotions) {
        for (OrderItem orderItem : orderExistingPromotions) {
            if (orderService.isQuantityBiggerThanPromo(orderItem, orderItem.getOrderQuantity())) {
                handlePromotion(orderItem);
            }
        }
    }

    private void handlePromotion(final OrderItem orderItem) {
        int remainQuantity = orderService.calcRemainQuantityAfterPromotionApply(orderItem);
        boolean remainQuantityBiggerThanPromoBuyNeed = orderService.isQuantityBiggerThanPromo(orderItem, remainQuantity);
        if (remainQuantity != 0 && remainQuantityBiggerThanPromoBuyNeed) {
            handleRemainingProducts(orderItem, remainQuantity);
        }
    }

    private void handleRemainingProducts(final OrderItem orderItem, final int remainQuantity) {
        if (orderService.isRemainingQuantityAvailableInPromoStock(orderItem, remainQuantity)) {
            askAddProduct(orderItem);
            return;
        }
        askRemainProductNoPromoFine(orderItem, remainQuantity);
    }

    private void askAddProduct(final OrderItem orderItem) {
        String productName = orderItem.getOrderProductName();
        int freeQuantity = orderService.getOrderItemPromotionFreeQuantity(orderItem);

        if (readAvailablePromotionProductAdd(productName, freeQuantity)) {
            orderService.buyPromoFreeProduct(orderItem);
        }
    }

    private void askRemainProductNoPromoFine(final OrderItem orderItem, final int remainQuantityCantApplyPromo) {
        String productName = orderItem.getOrderProductName();
        if (!readNoPromoFine(productName, remainQuantityCantApplyPromo)) {
            cancelNoPromoQuantity(orderItem, remainQuantityCantApplyPromo);
        }
    }

    private void cancelNoPromoQuantity(final OrderItem orderItem, final int remainQuantityCantApplyPromo) {
        orderService.cancelOrderItemAsQuantity(orderItem, remainQuantityCantApplyPromo);
        outputView.printCancelNoPromoQuantity(orderItem.getOrderProductName(), remainQuantityCantApplyPromo);
    }

    private boolean readAvailablePromotionProductAdd(final String productName, final int freeQuantity) {
        return executeWithRetry(() -> {
            String customerAnswer = inputView.readAvailablePromotionProductAdd(productName, freeQuantity);
            DataTypeValidator.validateYOrN(customerAnswer);
            return customerAnswer.equals(ANSWER_YES);
        });
    }

    private boolean readNoPromoFine(final String productName, final int remainQuantity) {
        return executeWithRetry(() -> {
            String customerAnswer = inputView.readNoPromoFine(productName, remainQuantity);
            DataTypeValidator.validateYOrN(customerAnswer);
            return customerAnswer.equals(ANSWER_YES);
        });
    }

    private static <T> T executeWithRetry(final Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
