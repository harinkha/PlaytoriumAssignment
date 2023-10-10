package com.example.platorium_assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentageDiscount extends Discount {
    private Double percentage;

    @Override
    public double applyDiscount(double total, List<CartItem> cartItems) {
        if(this.percentage == null || this.percentage < 0){
            return total;
        }
        return total - (total * percentage / 100);
    }
    @Override
    public DiscountType getDiscountType() {
        return DiscountType.COUPON;
    }
}