package com.example.demo.pricing.decorator;

public class TaxDecorator extends PriceDecorator {
    private final double taxRate;

    public TaxDecorator(BaseAppointmentPrice decoratedPrice, double taxRate) {
        super(decoratedPrice);
        this.taxRate = taxRate;
    }

    @Override
    public double calculatePrice() {
        return decoratedPrice.calculatePrice() * (1 + taxRate);
    }

    @Override
    public String getDescription() {
        return decoratedPrice.getDescription() + " + مالیات (" + (taxRate * 100) + "%)";
    }
}