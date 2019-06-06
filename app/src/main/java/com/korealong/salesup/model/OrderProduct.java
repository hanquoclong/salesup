package com.korealong.salesup.model;

public class OrderProduct {
    public int productID,productPrice;
    public String productName;

    public OrderProduct(int productID, int productPrice, String productName) {
        this.productID = productID;
        this.productPrice = productPrice;
        this.productName = productName;
    }
}
