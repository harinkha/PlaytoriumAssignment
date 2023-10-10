package com.example.platorium_assignment.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "discountType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedAmountDiscount.class, name = "FIXED_AMOUNT"),
        @JsonSubTypes.Type(value = PercentageDiscount.class, name = "PERCENTAGE"),
        @JsonSubTypes.Type(value = PointDiscount.class, name = "POINTS"),
        @JsonSubTypes.Type(value = CategoryPercentageDiscount.class, name = "CATEGORY"),
        @JsonSubTypes.Type(value = SeasonalDiscount.class, name = "SEASONAL")

})
public abstract class Discount {
    public abstract double applyDiscount(double total, List<CartItem> cartItems);

    public abstract DiscountType getDiscountType();
}
