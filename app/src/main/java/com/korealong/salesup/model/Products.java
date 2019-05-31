package com.korealong.salesup.model;

public class Products {
    public int idProduct;
    public int idFac;
    public String nameProduct;
    public String description;
    public int unitPrice;
    public int inStock;
    public String img;

    public Products(int idProduct, int idFac, String nameProduct, String description, int unitPrice, int inStock, String img) {
        this.idProduct = idProduct;
        this.idFac = idFac;
        this.nameProduct = nameProduct;
        this.description = description;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
        this.img = img;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdFac() {
        return idFac;
    }

    public void setIdFac(int idFac) {
        this.idFac = idFac;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
