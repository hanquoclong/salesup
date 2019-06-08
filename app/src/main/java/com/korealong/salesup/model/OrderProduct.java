package com.korealong.salesup.model;

public class OrderProduct {
    public int productID,productPrice, number;
    public String productName;

    public OrderProduct(int productID, int productPrice, int number, String productName) {
        this.productID = productID;
        this.productPrice = productPrice;
        this.number = number;
        this.productName = productName;
    }
}
