package com.example.demo.history.memento;

import java.time.LocalDateTime;

public class AppointmentMemento {
    private final Long appointmentId;
    private final String status;
    private final String paymentStatus;
    private final String changeType;
    private final String description;
    private final LocalDateTime timestamp;

    public AppointmentMemento(Long appointmentId, String status, String paymentStatus, String changeType, String description) {
        this.appointmentId = appointmentId;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.changeType = changeType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getChangeType() {
        return changeType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "AppointmentMemento{" +
                "appointmentId=" + appointmentId +
                ", status='" + status + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", changeType='" + changeType + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}