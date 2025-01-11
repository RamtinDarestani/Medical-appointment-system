package com.example.demo.pricing.decorator;

public class DiscountDecorator extends PriceDecorator {
    private final double discountPercentage;
    private final String reason;

    public DiscountDecorator(BaseAppointmentPrice price, double discountPercentage, String reason) {
        super(price);
        this.discountPercentage = discountPercentage;
        this.reason = reason;
    }

    @Override
    public double calculatePrice() {
        return decoratedPrice.calculatePrice() * (1 - discountPercentage);
    }

    @Override
    public String getDescription() {
        return String.format("%s با %d%% تخفیف (%s)",
                decoratedPrice.getDescription(),
                (int)(discountPercentage * 100),
                reason
        );
    }
}