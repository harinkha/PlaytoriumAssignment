package com.example.platorium_assignment.dto;

import com.example.platorium_assignment.model.CartItem;
import com.example.platorium_assignment.model.Discount;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDiscountRequest {
    private List<CartItem> cartItems;
    private List<Discount> discounts;
}
