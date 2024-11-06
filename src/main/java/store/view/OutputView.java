package store.view;

import java.text.NumberFormat;

public class OutputView {
    private static final String OUTPUT_GREETINGS = "안녕하세요. W편의점입니다.";
    private static final String OUTPUT_CURRENT_PRODUCT_INVENTORY = "현재 보유하고 있는 상품입니다.";
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static final String PRODUCT_TEMPLATE = "- %s %s원 %d개 %s";

    public void printGreetings() {
        System.out.println(OUTPUT_GREETINGS);
    }

    public void printCurrentInventory() {
        System.out.println(OUTPUT_CURRENT_PRODUCT_INVENTORY);
    }

}
