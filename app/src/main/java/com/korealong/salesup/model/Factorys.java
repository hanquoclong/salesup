package com.korealong.salesup.model;

public class Factorys {
    public int idFactory;
    public String nameFactory;

    public Factorys(int idFactory, String nameFactory) {
        this.idFactory = idFactory;
        this.nameFactory = nameFactory;
    }

    public int getIdFactory() {
        return idFactory;
    }

    public void setIdFactory(int idFactory) {
        this.idFactory = idFactory;
    }

    public String getNameFactory() {
        return nameFactory;
    }

    public void setNameFactory(String nameFactory) {
        this.nameFactory = nameFactory;
    }
}
