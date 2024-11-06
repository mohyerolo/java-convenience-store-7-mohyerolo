package store.view;

import store.domain.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String OUTPUT_GREETINGS = "안녕하세요. W편의점입니다.";
    private static final String OUTPUT_CURRENT_PRODUCT_INVENTORY = "현재 보유하고 있는 상품입니다.";
    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public static final String PRODUCT_TEMPLATE = "- %s %s원 %d개";

    public void printGreetings() {
        System.out.println(OUTPUT_GREETINGS);
    }

    public void printCurrentInventory() {
        System.out.println(OUTPUT_CURRENT_PRODUCT_INVENTORY);
    }

    public void printProductInventory(Map<String, List<Product>> products) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Product>> entry : products.entrySet()) {
            for (Product product : entry.getValue()) {
                sb.append(makeProductStatusSentence(product)).append('\n');
            }
        }
        System.out.println(sb);
    }

    // TODO: promotion을 enum 사용해서 하도록 수정
    private String makeProductStatusSentence(Product product) {
        String defaultSentence = makeDefaultProductSentence(product);
        if (product.getPromotion().equals("null")) {
            return defaultSentence;
        }
        return defaultSentence + " " + product.getPromotion();
    }

    private String makeDefaultProductSentence(Product product) {
        return String.format(PRODUCT_TEMPLATE, product.getName(), numberFormat.format(product.getPrice()), product.getQuantity());
    }

}
