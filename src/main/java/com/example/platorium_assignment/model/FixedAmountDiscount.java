package com.example.platorium_assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixedAmountDiscount extends Discount {
    private Double amount;

    @Override
    public double applyDiscount(double total, List<CartItem> cartItems) {
        if(this.amount == null || this.amount < 0){
            return total;
        }
        return total - amount;
    }
    @Override
    public DiscountType getDiscountType() {
        return DiscountType.COUPON;
    }

}