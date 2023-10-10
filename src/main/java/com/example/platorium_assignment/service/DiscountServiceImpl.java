package com.example.platorium_assignment.service;

import com.example.platorium_assignment.exception.EmptyCartException;
import com.example.platorium_assignment.model.CartItem;
import com.example.platorium_assignment.model.Discount;
import com.example.platorium_assignment.model.DiscountType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService{

    public double applyDiscounts(List<CartItem> cartItems, List<Discount> discounts) {

        if(cartItems == null ){
            throw new EmptyCartException("Items in the cart cannot be empty to calculate discount");
        }

        double total = computeInitialTotal(cartItems);

        List<Discount> couponDiscounts = discounts.stream().filter(d -> d.getDiscountType() == DiscountType.COUPON).collect(Collectors.toList());
        List<Discount> onTopDiscounts = discounts.stream().filter(d -> d.getDiscountType() == DiscountType.ON_TOP).collect(Collectors.toList());
        List<Discount> seasonalDiscounts = discounts.stream().filter(d -> d.getDiscountType() == DiscountType.SEASONAL).collect(Collectors.toList());

        if (!couponDiscounts.isEmpty()) {
            total = applyBestDiscount(total, cartItems, couponDiscounts);
        }

        if (!onTopDiscounts.isEmpty()) {
            total = applyBestDiscount(total, cartItems, onTopDiscounts);
        }

        if (!seasonalDiscounts.isEmpty()) {
            total = applyBestDiscount(total, cartItems, seasonalDiscounts);
        }
        if(total < 0){
            return 0;
        }

        return total;
    }

    private double applyBestDiscount(double total, List<CartItem> cartItems, List<Discount> discounts) {
        double bestDiscountedTotal = total;

        for (Discount discount : discounts) {
            double newTotal = discount.applyDiscount(total, cartItems);
            bestDiscountedTotal = Math.min(bestDiscountedTotal, newTotal);
        }

        return bestDiscountedTotal;
    }

    private double computeInitialTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> Math.max(0, item.getPrice()))
                .sum();
    }
}
