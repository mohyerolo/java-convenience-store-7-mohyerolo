package store.controller;

import store.domain.Product;
import store.service.StoreService;
import store.util.FileReaderUtil;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

public class StoreController {
    private static final String PRODUCTS_FILE = "products.md";

    private static final String PROMOTIONS_FILE = "promotions.md";


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

        List<String> productData = FileReaderUtil.readFile(PRODUCTS_FILE);
        Map<String, List<Product>> organizedProducts = storeService.parseProducts(productData);
        outputView.printProductInventory(organizedProducts);
    }
}
