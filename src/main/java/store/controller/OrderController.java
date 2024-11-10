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

    public Order takeOrder(Store store) {
        Order order = createOrder(store);
        checkPromotion(order);
        return order;
    }

    public boolean checkOrderStillAvailable(Order order) {
        return orderService.checkOrderStillAvailable(order);
    }

    private Order createOrder(final Store store) {
        return executeWithRetry(() -> {
            String orders = inputView.readBuyProductAndQuantity();
            return orderService.createOrder(orders, store);
        });
    }

    private void checkPromotion(Order order) {
        List<OrderItem> promotionExistOrderItems = orderService.checkPromotionApplied(order);
        readCustomersPromotionStatusOpinion(promotionExistOrderItems);
        orderService.applyPromotionsToOrder(order, promotionExistOrderItems);
    }

    private void readCustomersPromotionStatusOpinion(final List<OrderItem> orderAppliedPromotions) {
        for (OrderItem orderItem : orderAppliedPromotions) {
            int remainQuantity = orderItem.calcRemainQuantityAfterPromotionApply();
            if (remainQuantity != 0) {
                handleRemainingProducts(orderItem, remainQuantity);
            }
        }
    }

    private void handleRemainingProducts(final OrderItem orderItem, final int remainQuantity) {
        if (orderItem.isRemainingQuantityAvailableInPromoStock(remainQuantity)) {
            askAddProduct(orderItem);
            return;
        }
        askRemainProductNoPromoFine(orderItem, remainQuantity);
    }

    private void askAddProduct(final OrderItem orderItem) {
        String productName = orderItem.getOrderProductName();
        int freeQuantity = orderItem.getFreeProductQuantity();
        String answer = readAvailablePromotionProductAdd(productName, freeQuantity);

        if (answer.equals(ANSWER_YES)) {
            orderItem.buyMorePromoProduct();
        }
    }

    private void askRemainProductNoPromoFine(final OrderItem orderItem, final int remainQuantityCantApplyPromo) {
        String productName = orderItem.getOrderProductName();
        String answer = readNoPromoFine(productName, remainQuantityCantApplyPromo);
        if (answer.equals(ANSWER_YES)) {
            return;
        }
        cancelNoPromoQuantity(orderItem, remainQuantityCantApplyPromo);
    }

    private void cancelNoPromoQuantity(final OrderItem orderItem, final int remainQuantityCantApplyPromo) {
        orderItem.cancelOrder(remainQuantityCantApplyPromo);
        outputView.printCancelNoPromoQuantity(orderItem.getOrderProductName(), remainQuantityCantApplyPromo);
    }

    private String readAvailablePromotionProductAdd(final String productName, final int freeQuantity) {
        return executeWithRetry(() -> {
            String customerAnswer = inputView.readAvailablePromotionProductAdd(productName, freeQuantity);
            DataTypeValidator.validateYOrN(customerAnswer);
            return customerAnswer;
        });
    }

    private String readNoPromoFine(final String productName, final int remainQuantity) {
        return executeWithRetry(() -> {
            String customerAnswer = inputView.readNoPromoFine(productName, remainQuantity);
            DataTypeValidator.validateYOrN(customerAnswer);
            return customerAnswer;
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
