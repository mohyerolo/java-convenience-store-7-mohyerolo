package store.service;

import store.domain.Store;
import store.domain.product.ProductStorage;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionFactory;
import store.domain.promotion.Promotions;
import store.util.FileReaderUtil;

import java.util.List;
import java.util.stream.Collectors;

public class StoreService {
    private static final String PRODUCTS_FILE = "products.md";

    private static final String PROMOTIONS_FILE = "promotions.md";
    private final ProductService productService;

    public StoreService(final ProductService productService) {
        this.productService = productService;
    }

    public Store makeConvenienceStore() {
        Promotions promotions = makeConvenienceStorePromotion();
        ProductStorage productStorage = makeConvenienceStoreProduct(promotions);
        return new Store(productStorage, promotions);
    }

    private ProductStorage makeConvenienceStoreProduct(final Promotions promotions) {
        List<String> productData = FileReaderUtil.readFile(PRODUCTS_FILE);
        return productService.parseProducts(productData, promotions);
    }

    private Promotions makeConvenienceStorePromotion() {
        List<String> promotionData = FileReaderUtil.readFile(PROMOTIONS_FILE);
        return new Promotions(parsePromotion(promotionData));
    }

    private List<Promotion> parsePromotion(final List<String> promotionData) {
        return promotionData.stream()
                .map(PromotionFactory::createPromotion)
                .collect(Collectors.toList());
    }

}
