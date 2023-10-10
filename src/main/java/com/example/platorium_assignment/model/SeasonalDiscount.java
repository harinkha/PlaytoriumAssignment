package com.example.platorium_assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonalDiscount extends Discount {
    private Double everyXTHB;
    private Double discountYTHB;

    @Override
    public double applyDiscount(double total, List<CartItem> cartItems) {
        if(this.everyXTHB == null || this.everyXTHB <= 0){
            return total;
        }
        if (this.discountYTHB == null || this.discountYTHB < 0) {
            return total;
        }
        int discountTimes = (int) (total / everyXTHB);
        return total - (discountTimes * discountYTHB);
    }
    @Override
    public DiscountType getDiscountType() {
        return DiscountType.SEASONAL;
    }
}
