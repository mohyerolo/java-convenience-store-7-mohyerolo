package store.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(final List<String> promotions) {
        this.promotions = promotions.stream()
                .map(PromotionFactory::createPromotion)
                .collect(Collectors.toUnmodifiableList());
    }
}
