package store.controller;

import store.domain.Store;
import store.domain.order.Order;
import store.dto.ProductStorageDto;
import store.service.OrderService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.function.Supplier;

public class StoreController {
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
