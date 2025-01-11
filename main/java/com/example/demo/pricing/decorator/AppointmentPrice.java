package com.example.demo.pricing.decorator;

public class AppointmentPrice implements BaseAppointmentPrice {
    private final double basePrice;
    private final String doctorSpecialty;

    public AppointmentPrice(double basePrice, String doctorSpecialty) {
        this.basePrice = basePrice;
        this.doctorSpecialty = doctorSpecialty;
    }

    @Override
    public double calculatePrice() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        return "ویزیت پایه " + doctorSpecialty;
    }
}