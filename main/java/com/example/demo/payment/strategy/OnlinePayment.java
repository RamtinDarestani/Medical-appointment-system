package com.example.demo.payment.strategy;

import java.time.LocalDateTime;
import java.util.UUID;

public class OnlinePayment implements PaymentStrategy {
    private String transactionId;
    private LocalDateTime paymentTime;
    private String cardNumber;
    private String bankName;

    public OnlinePayment() {
        this.transactionId = generateTransactionId();
        this.paymentTime = LocalDateTime.now();
    }

    public OnlinePayment(String cardNumber, String bankName) {
        this();
        this.cardNumber = cardNumber;
        this.bankName = bankName;
    }

    @Override
    public boolean processPayment(double amount) {
        this.transactionId = generateTransactionId();
        this.paymentTime = LocalDateTime.now();
        return processOnlineTransaction(amount);
    }

    @Override
    public String getPaymentMethod() {
        return "ONLINE";
    }

    @Override
    public double calculatePaymentAmount(double originalAmount) {
        return originalAmount;
    }

    private boolean processOnlineTransaction(double amount) {
        return connectToPaymentGateway(amount);
    }

    private boolean connectToPaymentGateway(double amount) {
        return true;
    }

    private String generateTransactionId() {
        return "TRX-" + UUID.randomUUID().toString();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}