package com.example.platorium_assignment.controller;

import com.example.platorium_assignment.dto.CartDiscountRequest;
import com.example.platorium_assignment.dto.DiscountResponse;
import com.example.platorium_assignment.model.CartItem;
import com.example.platorium_assignment.model.Discount;
import com.example.platorium_assignment.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/apply-discounts")
    public ResponseEntity<DiscountResponse> applyDiscounts(@RequestBody CartDiscountRequest cartRequest) {
        List<CartItem> cartItems = cartRequest.getCartItems();
        List<Discount> discounts = cartRequest.getDiscounts();
        double finalTotal = discountService.applyDiscounts(cartItems, discounts);
        DiscountResponse discountResponse=new DiscountResponse(finalTotal);
        return ResponseEntity.ok(discountResponse);
    }
}
