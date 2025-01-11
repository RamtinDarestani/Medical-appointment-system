package com.example.demo.payment.strategy;

public interface PaymentStrategy {
    boolean processPayment(double amount);
    String getPaymentMethod();
    double calculatePaymentAmount(double originalAmount);
}
