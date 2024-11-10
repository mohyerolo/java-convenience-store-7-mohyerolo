package store.controller;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.dto.ProductStorageDto;
import store.dto.ReceiptDto;
import store.service.OrderService;
import store.service.StoreService;
import store.validator.DataTypeValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class StoreController {
    private static final String ANSWER_YES = "Y";
    private static final String ANSWER_NO = "N";

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final OrderService orderService;

    public StoreController(final InputView inputView, final OutputView outputView,
                           final StoreService storeService, final OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.orderService = orderService;
    }

    public void purchase() {
        boolean shopping = true;
        Store store = storeService.makeConvenienceStore();

        while (shopping) {
            printStore(store);
            Order order = takeCustomerOrder(store);
            if (order.checkOrderItemStillExists()) {
                printReceipt(order, readMembership());
                storeService.updateProductStorage(store, order);
            }
            shopping = readBuyMore();
            outputView.printNewLine();
        }
    }

    private void printStore(Store store) {
        outputView.printGreetings();
        outputView.printCurrentInventory();
        outputView.printProductStorage(makeProductDto(store));
    }

    private Order takeCustomerOrder(Store store) {
        Order order = takeOrder(store);
        List<OrderItem> promotionExistOrderItems = orderService.checkPromotionApplied(order);
        readCustomersPromotionStatusOpinion(promotionExistOrderItems);
        orderService.applyPromotionsToOrder(order, promotionExistOrderItems);
        return order;
    }

    private ProductStorageDto makeProductDto(final Store store) {
        return ProductStorageDto.from(store.getProductStorage());
    }

    private Order takeOrder(final Store store) {
        return executeWithRetry(() -> {
            String orders = inputView.readBuyProductAndQuantity();
            return orderService.takeOrder(orders, store);
        });
    }

    private void readCustomersPromotionStatusOpinion(final List<OrderItem> orderAppliedPromotions) {
        for (OrderItem orderItem : orderAppliedPromotions) {
            if (orderItem.isPromotionWellApplied()) {
                continue;
            }
            handleRemainingProducts(orderItem);
        }
    }

    private void handleRemainingProducts(final OrderItem orderItem) {
        int remainQuantityCantApplyPromo = orderItem.calcRemainQuantityAfterPromotionApply();
        if (orderItem.isRemainQuantityCanAppliedPromotionProduct(remainQuantityCantApplyPromo)) {
            askAddProduct(orderItem);
            return;
        }
        askRemainProductNoPromoFine(orderItem, remainQuantityCantApplyPromo);
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

    private boolean readMembership() {
        return executeWithRetry(() -> {
            String answer = inputView.readMembership();
            DataTypeValidator.validateYOrN(answer);
            return answer.equals(ANSWER_YES);
        });
    }

    private void printReceipt(Order order, boolean membership) {
        ReceiptDto receipt = ReceiptDto.from(order);
        outputView.printReceipt(receipt, membership);
    }

    private boolean readBuyMore() {
        return executeWithRetry(() -> {
            String answer = inputView.readBuyOtherProduct();
            DataTypeValidator.validateYOrN(answer);
            return answer.equals(ANSWER_YES);
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
