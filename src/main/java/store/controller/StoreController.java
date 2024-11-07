package store.controller;

import store.domain.Store;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

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
        outputView.printProductInventory(store.getProducts());


    }
}
