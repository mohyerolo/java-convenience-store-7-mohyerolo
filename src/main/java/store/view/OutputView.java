package store.view;

import store.dto.ProductDto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String OUTPUT_GREETINGS = "안녕하세요. W편의점입니다.";
    private static final String OUTPUT_CURRENT_PRODUCT_INVENTORY = "현재 보유하고 있는 상품입니다.";
    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static final String PRODUCT_TEMPLATE = "- %s %s원 %d개";
    private static final String PRODUCT_STOCK_OUT_TEMPLATE = "- %s %s원 재고없음";

    public void printGreetings() {
        System.out.println(OUTPUT_GREETINGS);
    }

    public void printCurrentInventory() {
        System.out.println(OUTPUT_CURRENT_PRODUCT_INVENTORY);
    }

    public void printProductInventory(Map<String, List<ProductDto>> products) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<ProductDto>> entry : products.entrySet()) {
            for (ProductDto product : entry.getValue()) {
                sb.append(makeProductStatusSentence(product)).append('\n');
            }
        }
        System.out.println(sb);
    }

    private String makeProductStatusSentence(ProductDto product) {
        if (product.getQuantity() == 0) {
            return makeStockOutSentence(product);
        }

        String defaultSentence = makeDefaultProductSentence(product);
        return appendPromotion(defaultSentence, product);
    }

    private String appendPromotion(String sentence, ProductDto product) {
        if (product.getPromotion() == null) {
            return sentence;
        }
        return sentence + " " + product.getPromotion();
    }

    private String makeStockOutSentence(ProductDto product) {
        return String.format(PRODUCT_STOCK_OUT_TEMPLATE, product.getName(), numberFormat.format(product.getPrice()));
    }

    private String makeDefaultProductSentence(ProductDto product) {
        return String.format(PRODUCT_TEMPLATE, product.getName(), numberFormat.format(product.getPrice()), product.getQuantity());
    }

}
