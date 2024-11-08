package store.domain.promotion;

import store.constant.PromotionType;
import store.validator.DataTypeValidator;
import store.validator.FileValidator;

import java.time.LocalDate;

public class PromotionFactory {
    private static final String DELIMITER = ",";

    public static Promotion createPromotion(final String promotionData) {
        String[] promotionFields = parsePromotionData(promotionData);
        validatePromotionFields(promotionFields);
        return buildPromotion(promotionFields);
    }

    private static String[] parsePromotionData(final String promotionData) {
        return promotionData.split(DELIMITER);
    }

    private static void validatePromotionFields(final String[] promotionFields) {
        FileValidator.validatePromotionField(promotionFields);
        DataTypeValidator.validateString(promotionFields[0]);
        DataTypeValidator.validateInt(promotionFields[1]);
        DataTypeValidator.validateInt(promotionFields[2]);
        DataTypeValidator.validateDate(promotionFields[3]);
        DataTypeValidator.validateDate(promotionFields[4]);
    }

    private static Promotion buildPromotion(final String[] promotionFields) {
        String name = promotionFields[0];
        PromotionType promotionType = PromotionType.from(promotionFields[1]);
        LocalDate startDate = LocalDate.parse(promotionFields[3]);
        LocalDate endDate = LocalDate.parse(promotionFields[4]);
        return new Promotion(name, promotionType, startDate, endDate);
    }
}
