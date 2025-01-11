package com.example.demo.payment.strategy;

public enum PaymentStatus {
    PENDING("در انتظار پرداخت"),
    PROCESSING("در حال پرداخت"),
    PAID("پرداخت شده"),
    FAILED("ناموفق");

    private final String persianTitle;

    PaymentStatus(String persianTitle) {
        this.persianTitle = persianTitle;
    }

    public String getPersianTitle() {
        return persianTitle;
    }
}