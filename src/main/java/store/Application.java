package store;

import store.controller.StoreController;
import store.service.OrderService;
import store.domain.product.ProductStorageFactory;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController(new InputView(), new OutputView(),
                new StoreService(), new OrderService());
        storeController.purchase();
    }
}
