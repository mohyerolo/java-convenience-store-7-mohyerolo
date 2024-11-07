package store.controller;

import store.domain.Product;
import store.domain.Store;
import store.dto.ProductDto;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        outputView.printProductInventory(makeProductDto(store.getProducts()));

    }

    private Map<String, List<ProductDto>> makeProductDto(Map<String, List<Product>> products) {
        return products.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ProductDto::new)
                                .collect(Collectors.toUnmodifiableList())
                ));
    }
}
