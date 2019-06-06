package com.korealong.salesup.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int productID;
    public int factoryID;
    public String nameProduct;
    public String description;
    public int unitPrice;
    public int inStock;
    public int saleID;

    public Product(int idProduct, int idFac, String nameProduct, String description, int unitPrice, int inStock, int saleID) {
        this.productID = idProduct;
        this.factoryID = idFac;
        this.nameProduct = nameProduct;
        this.description = description;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
        this.saleID = saleID;
    }
}
