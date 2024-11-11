package store.controller;

import store.domain.Receipt;
import store.domain.Store;
import store.domain.order.Order;
import store.dto.ProductDto;
import store.dto.ReceiptDto;
import store.service.ProductService;
import store.service.ReceiptService;
import store.service.StoreService;
import store.validator.DataTypeValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class StoreController {
    private static final String ANSWER_YES = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final OrderController orderController;
    private ProductService productService;
    private ReceiptService receiptService;

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
        productService = new ProductService(store.getProductStorage());
        receiptService = new ReceiptService();

        while (shopping) {
            exploreConvenienceStore(store);
            shopping = readBuyMore();
            outputView.printNewLine();
        }
    }

    private void exploreConvenienceStore(final Store store) {
        printStore();
        Order order = orderController.takeOrder(store);
        if (orderController.hasValidOrderItems(order)) {
            Receipt receipt = Receipt.from(order);
            printReceipt(receipt, readMembership());
            storeService.updateProductStorage(store, order);
        }
    }

    private void printStore() {
        outputView.printGreetings();
        outputView.printCurrentInventory();
        outputView.printProductStorage(makeProductDto());
    }

    private List<ProductDto> makeProductDto() {
        return productService.makeProductToDto();
    }

    private boolean readMembership() {
        return executeWithRetry(() -> {
            String answer = inputView.readMembership();
            DataTypeValidator.validateYOrN(answer);
            return answer.equals(ANSWER_YES);
        });
    }

    private void printReceipt(final Receipt receipt, final boolean membership) {
        ReceiptDto receiptDto = receiptService.makeReceiptToDto(receipt, membership);
        outputView.printReceipt(receiptDto);
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
