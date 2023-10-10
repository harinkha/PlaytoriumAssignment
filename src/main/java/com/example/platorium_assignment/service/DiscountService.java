package com.example.platorium_assignment.service;

import com.example.platorium_assignment.model.CartItem;
import com.example.platorium_assignment.model.Discount;

import java.util.List;

public interface DiscountService {
    double applyDiscounts(List<CartItem> cartItems, List<Discount> discounts);
}
