package store.config;

import store.controller.OrderController;
import store.controller.StoreController;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {
    public StoreController setting() {
        InputView inputView = inputView();
        OutputView outputView = outputView();
        return new StoreController(inputView, outputView(), storeService(),
                orderController(inputView, outputView));
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private StoreService storeService() {
        return new StoreService();
    }

    private OrderController orderController(final InputView inputView, final OutputView outputView) {
        return new OrderController(inputView, outputView);
    }

}
