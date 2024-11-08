package store.controller;

import store.domain.Store;
import store.dto.ProductStorageDto;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.function.Supplier;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;

    public StoreController(final InputView inputView, final OutputView outputView, final StoreService storeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
    }

    public void purchase() {
        outputView.printGreetings();
        outputView.printCurrentInventory();

        Store store = storeService.makeConvenienceStore();
        outputView.printProductStorage(makeProductDto(store));

        executeWithRetry(inputView::readBuyProductAndQuantity);
    }

    private ProductStorageDto makeProductDto(Store store) {
        return ProductStorageDto.from(store.getProductStorage());
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
