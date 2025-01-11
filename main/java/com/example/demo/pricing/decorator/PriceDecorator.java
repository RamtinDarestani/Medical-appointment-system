package com.example.demo.pricing.decorator;

public abstract class PriceDecorator implements BaseAppointmentPrice {
    protected final BaseAppointmentPrice decoratedPrice;

    public PriceDecorator(BaseAppointmentPrice decoratedPrice) {
        this.decoratedPrice = decoratedPrice;
    }

    @Override
    public abstract double calculatePrice();

    @Override
    public abstract String getDescription();
}