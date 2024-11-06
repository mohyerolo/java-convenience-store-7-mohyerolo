package store.domain;

public class Product {
    private static final String PRODUCT_STRING_EXP = "^(?!\\s*$)(?!\\s).+";
    private static final String PRODUCT_INT_EXP = "^[\\d]+$";

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(final String[] fileLine) {
        validate(fileLine);
        this.name = fileLine[0];
        this.price = Integer.parseInt(fileLine[1]);
        this.quantity = Integer.parseInt(fileLine[2]);
        this.promotion = fileLine[3];
    }

    private void validate(String[] fileLine) {
        validateProductField(fileLine);
        validateNameAndPromotion(fileLine);
        validatePriceAndQuantity(fileLine);
    }

    private void validateProductField(String[] fileLine) {
        if (fileLine.length < 4) {
            throw new IllegalArgumentException("[ERROR] 상품 정보 파일에 name,price,quantity,promotion 양식을 지키지 못한 라인이 있습니다.");
        }
    }

    private void validateNameAndPromotion(String[] fileLine) {
        if (!fileLine[0].matches(PRODUCT_STRING_EXP) || !fileLine[3].matches(PRODUCT_STRING_EXP)) {
            throw new IllegalArgumentException("[ERROR] 상품 정보 파일에 문자가 없는 칸이 존재합니다.");
        }
    }

    private void validatePriceAndQuantity(String[] fileLine) {
        if (!fileLine[1].matches(PRODUCT_INT_EXP) || !fileLine[2].matches(PRODUCT_INT_EXP)) {
            throw new IllegalArgumentException("[ERROR] 상품 정보 파일의 가격이나 수량에 숫자가 아닌 문자가 들어가있습니다.");
        }
    }
}
