package store.domain;

import store.constant.PromotionType;
import store.validator.DataTypeValidator;
import store.validator.FileValidator;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final PromotionType promotionType;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String[] fileLine) {
        validate(fileLine);
        this.name = fileLine[0];
        this.promotionType = PromotionType.from(fileLine[1]);
        this.startDate = LocalDate.parse(fileLine[3]);
        this.endDate = LocalDate.parse(fileLine[4]);
    }

    private void validate(final String[] fileLine) {
        FileValidator.validatePromotionField(fileLine);
        DataTypeValidator.validateString(fileLine[0]);
        DataTypeValidator.validateInt(fileLine[1]);
        DataTypeValidator.validateInt(fileLine[2]);
        DataTypeValidator.validateDate(fileLine[3]);
        DataTypeValidator.validateDate(fileLine[4]);
    }

}
