package com.example.platorium_assignment.service;



import com.example.platorium_assignment.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    private List<CartItem> cartItems;

    @BeforeEach
    public void setUp() {
        discountService = new DiscountServiceImpl();

        cartItems = Arrays.asList(
                new CartItem("Laptop", 1000, "Electronics"),
                new CartItem("Phone", 500, "Electronics"),
                new CartItem("Shirt", 50, "Clothing"),
                new CartItem("Jeans", 80, "Clothing"),
                new CartItem("Shoes", 200, "Footwear")
        );
    }

    @Test
    public void testPercentageDiscount() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new PercentageDiscount(10.0)));
        assertEquals(1647, total, 0.1);
    }

    @Test
    public void testFixedAmountDiscount() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new FixedAmountDiscount(100.0)));
        assertEquals(1730, total);
    }

    @Test
    public void testPointDiscount() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new PointDiscount(100)));
        assertEquals(1730, total);
    }

    @Test
    public void testSeasonalDiscount() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new SeasonalDiscount(500.0, 100.0)));
        assertEquals(1530, total);
    }

    @Test
    public void testCategoryPercentageDiscount() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new CategoryPercentageDiscount("Electronics", 10.0)));
        assertEquals(1680, total, 0.1);
    }

    @Test
    public void testBestOfCategoryDiscount() {
        double total = discountService.applyDiscounts(cartItems, Arrays.asList(
                new PercentageDiscount(10.0),
                new FixedAmountDiscount(200.0)
        ));
        assertEquals(1630, total); // The FixedAmountDiscount gives a better discount than PercentageDiscount
    }

    @Test
    public void testNegativeNumbers() {
        double total = discountService.applyDiscounts(cartItems, Collections.singletonList(new FixedAmountDiscount(-100.0)));
        assertEquals(1830, total); // The negative discount is treated as 0
    }

    @Test
    public void testAllDiscountsTogether() {
        double total = discountService.applyDiscounts(cartItems, Arrays.asList(
                new PercentageDiscount(10.0),
                new FixedAmountDiscount(100.0),
                new PointDiscount(100),
                new SeasonalDiscount(500.0, 100.0),
                new CategoryPercentageDiscount("Electronics", 10.0)
        ));

        assertEquals(1297.0, total); // The combination of all discounts
    }

    @Test
    public void testMultiplePercentageDiscounts() {
        double total = discountService.applyDiscounts(cartItems, Arrays.asList(
                new PercentageDiscount(10.0),
                new PercentageDiscount(5.0)
        ));
        assertEquals(1647, total); // Only the 10% discount should be applied.
    }

    @Test
    public void testNegativePriceCartItem() {
        List<CartItem> itemsWithNegative = new ArrayList<>(cartItems);
        itemsWithNegative.add(new CartItem("InvalidItem", -100, "Electronics"));
        double total = discountService.applyDiscounts(itemsWithNegative, Collections.singletonList(new PercentageDiscount(10.0)));
        assertEquals(1647, total); // The negative price item should be treated as 0.
    }

    @Test
    public void testNoDiscountExceedsInitialTotal() {
        double total = discountService.applyDiscounts(cartItems, Arrays.asList(
                new FixedAmountDiscount(5000.0),
                new PercentageDiscount(110.0),
                new PointDiscount(5000),
                new SeasonalDiscount(5000.0, 5000.0)
        ));
        assertEquals(0, total); // No discount should make the total negative.
    }

    @Test
    public void testMultipleDiscountTypesWithBestFromEachCategory() {
        List<CartItem> cartItems = Arrays.asList(
                new CartItem("T-Shirt", 100.0, "CLOTHING"),
                new CartItem("Summer Hat", 30.0, "ACCESSORIES"),
                new CartItem("Coffee", 10.0, "DRINK"),
                new CartItem("Sneakers", 250.0, "SHOES"),
                new CartItem("Laptop", 1200.0, "ELECTRONICS")
        );

        List<Discount> discounts = Arrays.asList(
                new FixedAmountDiscount(50.0),
                new PercentageDiscount(10.0), // 10% off total.
                new PointDiscount(40), // Max 20% of 1590 = 318. But, 40 points are less.
                new CategoryPercentageDiscount("CLOTHING", 20.0), // 20% off on T-Shirt = 20 off.
                new SeasonalDiscount(500.0, 20.0) // For every 500 spent, 20 off.
        );

        double result = discountService.applyDiscounts(cartItems, discounts);

        // Initial price: 1590.
        // FixedAmountDiscount: 1590 - 50 = 1540
        // PercentageDiscount: 1590 - 159 = 1431. Best among COUPON discounts.

        // PointDiscount: 40 off 1431 = 1391.
        // CategoryPercentageDiscount: 20 off 1431 = 1411. So, PointDiscount is better among ON_TOP discounts.

        // SeasonalDiscount: For every 500 spent, 20 off. So now the total is 1391, and it will apply 2 times: 1391-40 = 1351

        assertEquals(1351.0, result, 0.001);
    }
}


