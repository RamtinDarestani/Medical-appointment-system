package com.example.demo.payment.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstallmentPayment implements PaymentStrategy {
    private int numberOfInstallments;
    private double interestRate;
    private LocalDateTime firstPaymentDate;
    private List<InstallmentDetail> installments;

    public InstallmentPayment(int numberOfInstallments, double interestRate) {
        this.numberOfInstallments = numberOfInstallments;
        this.interestRate = interestRate;
        this.firstPaymentDate = LocalDateTime.now();
        this.installments = new ArrayList<>();
    }

    @Override
    public boolean processPayment(double amount) {
        double installmentAmount = calculateInstallmentAmount(amount);
        createInstallmentPlan(amount, installmentAmount);
        return processFirstInstallment(installmentAmount);
    }

    @Override
    public String getPaymentMethod() {
        return "INSTALLMENT";
    }

    @Override
    public double calculatePaymentAmount(double originalAmount) {
        return originalAmount * (1 + (interestRate * numberOfInstallments) / 100);
    }

    private double calculateInstallmentAmount(double totalAmount) {
        double totalAmountWithInterest = calculatePaymentAmount(totalAmount);
        return totalAmountWithInterest / numberOfInstallments;
    }

    private void createInstallmentPlan(double totalAmount, double installmentAmount) {
        installments.clear();
        LocalDateTime dueDate = firstPaymentDate;

        for (int i = 0; i < numberOfInstallments; i++) {
            InstallmentDetail installment = new InstallmentDetail(
                    i + 1,
                    installmentAmount,
                    dueDate,
                    i == 0 ? "PENDING" : "SCHEDULED"
            );
            installments.add(installment);
            dueDate = dueDate.plusMonths(1);
        }
    }

    private boolean processFirstInstallment(double amount) {
        if (!installments.isEmpty()) {
            InstallmentDetail firstInstallment = installments.get(0);
            firstInstallment.setStatus("PAID");
            firstInstallment.setPaymentDate(LocalDateTime.now());
            return true;
        }
        return false;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(LocalDateTime firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public List<InstallmentDetail> getInstallments() {
        return installments;
    }

    public void setInstallments(List<InstallmentDetail> installments) {
        this.installments = installments;
    }

    public static class InstallmentDetail {
        private int installmentNumber;
        private double amount;
        private LocalDateTime dueDate;
        private LocalDateTime paymentDate;
        private String status;

        public InstallmentDetail(int installmentNumber, double amount, LocalDateTime dueDate, String status) {
            this.installmentNumber = installmentNumber;
            this.amount = amount;
            this.dueDate = dueDate;
            this.status = status;
        }

        public int getInstallmentNumber() {
            return installmentNumber;
        }

        public void setInstallmentNumber(int installmentNumber) {
            this.installmentNumber = installmentNumber;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public LocalDateTime getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
        }

        public LocalDateTime getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
