package com.example.platorium_assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPercentageDiscount extends Discount {
    private String category;
    private Double percentage;

    @Override
    public double applyDiscount(double total, List<CartItem> cartItems) {
        if(this.percentage == null || this.percentage < 0){
            return total;
        }
        if(this.category == null){
            return total;
        }
        double discount = 0.0;
        for (CartItem item : cartItems) {
            if (item.getCategory() != null) {
                if (item.getCategory().equalsIgnoreCase(category)) {
                    discount += (item.getPrice() * percentage / 100);
                }
            }
        }

        return total - discount;
    }
    @Override
    public DiscountType getDiscountType() {
        return DiscountType.ON_TOP;
    }

}