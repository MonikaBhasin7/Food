package com.techsdm.food.Model;

/**
 * Created by Monika Bhasin on 07-08-2018.
 */

public class OrderItem {

    public String ProductName;
    public String Quantity;
    public String Price;
    public String Discount;

    public OrderItem() {
    }

    public OrderItem(String productName, String quantity, String price, String discount) {
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
