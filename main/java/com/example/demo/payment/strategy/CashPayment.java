package com.example.demo.payment.strategy;

import java.time.LocalDateTime;

public class CashPayment implements PaymentStrategy {
    private LocalDateTime paymentTime;
    private String receiptNumber;

    public CashPayment() {
        this.paymentTime = LocalDateTime.now();
        this.receiptNumber = generateReceiptNumber();
    }

    @Override
    public boolean processPayment(double amount) {
        this.paymentTime = LocalDateTime.now();
        return processCashTransaction(amount);
    }

    @Override
    public String getPaymentMethod() {
        return "CASH";
    }

    @Override
    public double calculatePaymentAmount(double originalAmount) {
        return originalAmount;
    }

    private boolean processCashTransaction(double amount) {
        this.receiptNumber = generateReceiptNumber();
        return true;
    }

    private String generateReceiptNumber() {
        return "CASH-" + System.currentTimeMillis();
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
}
