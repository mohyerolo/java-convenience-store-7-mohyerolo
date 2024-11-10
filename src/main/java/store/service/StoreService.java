package store.service;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.domain.product.ProductStorage;
import store.domain.product.ProductStorageFactory;
import store.domain.promotion.Promotions;
import store.util.FileReaderUtil;

import java.util.List;

public class StoreService {
    private static final String PRODUCTS_FILE = "products.md";
    private static final String PROMOTIONS_FILE = "promotions.md";

    public Store makeConvenienceStore() {
        Promotions promotions = makeConvenienceStorePromotion();
        ProductStorage productStorage = makeConvenienceStoreProduct(promotions);
        return new Store(productStorage, promotions);
    }

    public void updateProductStorage(final Store store, final Order order) {
        ProductStorage productStorage = store.getProductStorage();
        for (OrderItem orderItem : order.getOrders()) {
            String productName = orderItem.getOrderProductName();
            productStorage.reduceProductStock(productName, orderItem.getOrderQuantity());
        }
    }

    private Promotions makeConvenienceStorePromotion() {
        List<String> promotionData = FileReaderUtil.readFile(PROMOTIONS_FILE);
        return Promotions.from(promotionData);
    }

    private ProductStorage makeConvenienceStoreProduct(final Promotions promotions) {
        List<String> productData = FileReaderUtil.readFile(PRODUCTS_FILE);
        return ProductStorageFactory.parseProducts(productData, promotions);
    }

}
