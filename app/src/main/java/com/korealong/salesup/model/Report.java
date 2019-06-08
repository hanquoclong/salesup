package com.korealong.salesup.model;

public class Report {
    public String productName,date,status;
    public int reportID;

    public Report(String productName, String date, String status, int reportID) {
        this.productName = productName;
        this.date = date;
        this.status = status;
        this.reportID = reportID;
    }
}
