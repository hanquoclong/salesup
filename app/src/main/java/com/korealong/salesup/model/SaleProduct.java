package com.korealong.salesup.model;

import java.util.Date;

public class SaleProduct {

    public int saleID;
    public int saleType;
    public int productID;
    public String productName;
    public int saleNumber;
    public String orderDate;
    public int saleProductPrice;
    public String typeName;

    public SaleProduct(int saleID, int saleType, int productID, String productName, int saleNumber, String orderDate, int saleProductPrice, String typeName) {
        this.saleID = saleID;
        this.saleType = saleType;
        this.productID = productID;
        this.productName = productName;
        this.saleNumber = saleNumber;
        this.orderDate = orderDate;
        this.saleProductPrice = saleProductPrice;
        this.typeName = typeName;
    }
}
