package store.service;

import store.domain.Store;
import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.domain.product.ProductStorage;
import store.domain.product.ProductStorageFactory;
import store.domain.promotion.Promotions;
import store.exception.CustomIllegalArgException;
import store.util.FileReaderUtil;

import java.io.IOException;
import java.util.List;

public class StoreService {
    private static final String FILE_READ_ERROR = "[ERROR] 파일을 읽는 중 에러가 발생했습니다.";
    private static final String PRODUCTS_FILE = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE = "src/main/resources/promotions.md";

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
        List<String> promotionData;
        try {
            promotionData = FileReaderUtil.readFile(PROMOTIONS_FILE);
        } catch (IOException e) {
            throw new CustomIllegalArgException(FILE_READ_ERROR);
        }
        return Promotions.from(promotionData);
    }

    private ProductStorage makeConvenienceStoreProduct(final Promotions promotions) {
        List<String> productData;
        try {
            productData = FileReaderUtil.readFile(PRODUCTS_FILE);
        } catch (IOException e) {
            throw new CustomIllegalArgException(FILE_READ_ERROR);
        }
        return ProductStorageFactory.parseProducts(productData, promotions);
    }

}
