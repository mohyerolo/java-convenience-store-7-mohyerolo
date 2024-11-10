package store.controller;

import store.domain.Store;
import store.domain.order.Order;
import store.dto.ProductStorageDto;
import store.dto.ReceiptDto;
import store.service.StoreService;
import store.validator.DataTypeValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.function.Supplier;

public class StoreController {
    private static final String ANSWER_YES = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final OrderController orderController;

    public StoreController(final InputView inputView, final OutputView outputView,
                           final StoreService storeService, final OrderController orderController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.orderController = orderController;
    }

    public void open() {
        boolean shopping = true;
        Store store = storeService.makeConvenienceStore();

        while (shopping) {
            exploreConvenienceStore(store);
            shopping = readBuyMore();
            outputView.printNewLine();
        }
    }

    private void exploreConvenienceStore(final Store store) {
        printStore(store);
        Order order = orderController.takeOrder(store);
        if (orderController.checkOrderStillExists(order)) {
            printReceipt(order, readMembership());
            storeService.updateProductStorage(store, order);
        }
    }

    private void printStore(final Store store) {
        outputView.printGreetings();
        outputView.printCurrentInventory();
        outputView.printProductStorage(makeProductDto(store));
    }

    private ProductStorageDto makeProductDto(final Store store) {
        return ProductStorageDto.from(store);
    }

    private boolean readMembership() {
        return executeWithRetry(() -> {
            String answer = inputView.readMembership();
            DataTypeValidator.validateYOrN(answer);
            return answer.equals(ANSWER_YES);
        });
    }

    private void printReceipt(final Order order, final boolean membership) {
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
