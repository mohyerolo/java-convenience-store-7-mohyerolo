package store.domain.order;

import store.domain.Store;
import store.domain.product.Product;

public class OrderItemFactory {

    public static OrderItem createOrderItem(final String[] orderFields, final Store store) {
        Product orderProduct = getOrderProduct(orderFields[0], store);
        return OrderItem.of(orderFields[0], Integer.parseInt(orderFields[1]), orderProduct);
    }

    private static Product getOrderProduct(final String productName, final Store store) {
        return store.getOrderProduct(productName);
    }
}
