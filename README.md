# PlaytoriumAssignment
Platorium Discount Service is a backend implementation designed using Spring Boot and Java to calculate and apply various types of discounts to a shopping cart. The system supports different types of discounts like percentage-based, fixed amount, category-specific, point-based, and seasonal discounts.

This project runs of Java 17 and Maven 4.0.0

An example Post Request into the API /cart/apply-discounts would be like

```json
{
  "cartItems": [
    { "name": "T-Shirt", "price": 100.0, "category": "CLOTHING" },
    { "name": "Summer Hat", "price": 30.0, "category": "ACCESSORIES" },
    { "name": "Coffee", "price": 10.0, "category": "DRINK" },
    { "name": "Sneakers", "price": 250.0, "category": "SHOES" },
    { "name": "Laptop", "price": 1200.0, "category": "ELECTRONICS" }
  ],
  "discounts": [
    {
      "discountType": "FIXED_AMOUNT",
      "amount": 50.0
    },
    {
      "discountType": "PERCENTAGE",
      "percentage": 10.0
    },
    {
      "discountType": "POINTS",
      "points": 40
    },
    {
      "discountType": "CATEGORY",
      "category": "CLOTHING",
      "percentage": 20.0
    },
    {
      "discountType": "SEASONAL",
      "everyXTHB": 500.0,
      "discountYTHB": 20.0
    }
  ]
}

```

which would return

```json
{
    "finalTotal": 1351.0
}
```
To explain the logic, the total in the cart is 1590

FixedAmountDiscount: 1590 - 50 = 1540

PercentageDiscount: 1590 - 159 = 1431. we go with percentage discount as bot are in COUPON category and it is the better one.

Now that we left with 1431

PointDiscount: 40 off 1431 = 1391.

CategoryPercentageDiscount: 1431 - 20% of 100 = 1411. So, PointDiscount is better among ON_TOP discounts.

Now we have 1391. 

SeasonalDiscount: For every 500 spent, 20 off. So now the total is 1391, and it will apply 2 times: 1391-40 = 1351.

There are 5 type of discountTypes which are "FIXED_AMOUNT", "PERCENTAGE", "POINTS", "CATEGORY" and "SEASONAL" each with its own parameters as shown above.

I have written many test cases covering many scenarios. I have also handled null and negative inputs.

Please review the project and let me know if you have any feedback or suggestions.
