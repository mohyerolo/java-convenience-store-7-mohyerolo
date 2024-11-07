package store.controller;

import store.domain.Product;
import store.service.ProductService;
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
    private final ProductService productService;

    public StoreController(final InputView inputView, final OutputView outputView, final ProductService productService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.productService = productService;
    }

    public void purchase() {
        outputView.printGreetings();
        outputView.printCurrentInventory();

        List<String> productData = FileReaderUtil.readFile(PRODUCTS_FILE);
        Map<String, List<Product>> organizedProducts = productService.parseProducts(productData);
        outputView.printProductInventory(organizedProducts);

        List<String> promotionData = FileReaderUtil.readFile(PROMOTIONS_FILE);
    }
}
