package com.example.demo.payment.factory;

import com.example.demo.payment.strategy.*;

public class PaymentFactory {
    public static PaymentStrategy createPaymentStrategy(String paymentMethod, Object... args) {
        return switch (paymentMethod.toUpperCase()) {
            case "CASH" -> new CashPayment();
            case "ONLINE" -> {
                if (args.length >= 2) {
                    yield new OnlinePayment((String) args[0], (String) args[1]);
                } else {
                    yield new OnlinePayment();
                }
            }
            case "INSTALLMENT" -> {
                int installments = args.length > 0 ? (Integer) args[0] : 3;
                double interestRate = args.length > 1 ? (Double) args[1] : 5.0;
                yield new InstallmentPayment(installments, interestRate);
            }
            default -> throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        };
    }
}