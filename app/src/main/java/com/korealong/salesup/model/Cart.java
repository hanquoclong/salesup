package com.korealong.salesup.model;

public class Cart {
    public int cartID;
    public int productID;
    public String productImage;
    public int productNumber;
    public int unitPrice;
    public int totalPrice;
    public int salePrice;

    public Cart(int cartID, int productID, String productImage, int productNumber, int unitPrice, int totalPrice, int salePrice) {
        this.cartID = cartID;
        this.productID = productID;
        this.productImage = productImage;
        this.productNumber = productNumber;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.salePrice = salePrice;
    }
}
