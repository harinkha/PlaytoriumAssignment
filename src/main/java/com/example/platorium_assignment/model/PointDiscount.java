package com.example.platorium_assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDiscount extends Discount {
    private Integer points;

    @Override
    public double applyDiscount(double total, List<CartItem> cartItems) {
        if(this.points == null || this.points < 0){
            return total;
        }
        double discountAmount = Math.min(points, total * 0.2); // Max discount is capped at 20% of total price.
        return total - discountAmount;
    }
    @Override
    public DiscountType getDiscountType() {
        return DiscountType.ON_TOP;
    }
}