package store;

import store.controller.StoreController;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController(new InputView(), new OutputView(), new ProductService());
        storeController.purchase();
    }
}
