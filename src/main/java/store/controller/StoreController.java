package store.controller;

import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;

    public StoreController (final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void purchase() {
        outputView.printGreetings();
        outputView.printCurrentInventory();
    }
}
