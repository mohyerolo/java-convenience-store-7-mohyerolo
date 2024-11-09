package store.controller;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.dto.ProductStorageDto;
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
        outputView.printGreetings();
        outputView.printCurrentInventory();

        Store store = storeService.makeConvenienceStore();
        outputView.printProductStorage(makeProductDto(store));

        Order order = takeOrder(store);
        List<OrderItem> promotionExistOrderItems = orderService.checkPromotionApplied(order);
        readCustomersPromotionStatusOpinion(promotionExistOrderItems);
    }

    private ProductStorageDto makeProductDto(Store store) {
        return ProductStorageDto.from(store.getProductStorage());
    }

    private Order takeOrder(Store store) {
        return executeWithRetry(() -> {
            String orders = inputView.readBuyProductAndQuantity();
            return orderService.takeOrder(orders, store);
        });
    }

    private void readCustomersPromotionStatusOpinion(List<OrderItem> orderAppliedPromotions) {
        for (OrderItem orderItem : orderAppliedPromotions) {
            if (orderItem.isPromotionWellApplied()) {
                continue;
            }
            orderItemPromotionProductStockIsAvailable(orderItem);
        }
    }

    private void orderItemPromotionProductStockIsAvailable(OrderItem orderItem) {
        if (orderItem.isRemainQuantityCanAppliedPromotionProduct()) {
            askAddProduct(orderItem);
            return;
        }
    }

    private void askAddProduct(OrderItem orderItem) {
        String productName = orderItem.getOrderProductName();
        int freeQuantity = orderItem.getFreeProductQuantity();
        String answer = readAvailablePromotionProductAdd(productName, freeQuantity);

        if (answer.equals(ANSWER_YES)) {
            orderItem.buyMorePromoProduct();
        }
    }

    private String readAvailablePromotionProductAdd(String productName, int freeQuantity) {
        return executeWithRetry(() -> {
            String customerAnswer = inputView.readAvailablePromotionProductAdd(productName, freeQuantity);
            DataTypeValidator.validateYOrN(customerAnswer);
            return customerAnswer;
        });
    }

    private static <T> T executeWithRetry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
