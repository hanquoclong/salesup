package com.korealong.salesup.model;

public class Report {
    public String productName;
    public String date;
    public String status;

    public Report(String productName, String date, String status) {
        this.productName = productName;
        this.date = date;
        this.status = status;
    }
}
