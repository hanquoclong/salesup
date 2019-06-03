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
}
