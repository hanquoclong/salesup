package com.korealong.salesup.model;

public class SaleProduct {

    public int idsale = 0;
    public int idtype = 0;
    public int idproduct = 0;
    public int idfac = 0;
    public String nametype = "";
    public String nameproduct = "";
    public String description = "";
    public int unitprice = 0;
    public int instock = 0;
    public String img;

    public SaleProduct(int idsale, int idtype, int idproduct, int idfac, String nametype, String nameproduct, String description, int unitprice, int instock, String img) {
        this.idsale = idsale;
        this.idtype = idtype;
        this.idproduct = idproduct;
        this.idfac = idfac;
        this.nametype = nametype;
        this.nameproduct = nameproduct;
        this.description = description;
        this.unitprice = unitprice;
        this.instock = instock;
        this.img = img;
    }

    public int getIdsale() {
        return idsale;
    }

    public void setIdsale(int idsale) {
        this.idsale = idsale;
    }

    public int getIdtype() {
        return idtype;
    }

    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public int getIdfac() {
        return idfac;
    }

    public void setIdfac(int idfac) {
        this.idfac = idfac;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
