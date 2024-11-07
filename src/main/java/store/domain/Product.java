package store.domain;

import store.validator.FileValidator;
import store.validator.DataTypeValidator;

public class Product {
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

    public boolean existsPromotion() {
        return !promotion.equals("null");
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    private void validate(String[] fileLine) {
        FileValidator.validateProductField(fileLine);
        DataTypeValidator.validateString(fileLine[0]);
        DataTypeValidator.validateString(fileLine[3]);
        DataTypeValidator.validateInt(fileLine[1]);
        DataTypeValidator.validateInt(fileLine[2]);
    }
}
