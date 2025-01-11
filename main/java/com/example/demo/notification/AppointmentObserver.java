package com.example.demo.notification;
import com.example.demo.model.Appointment;

public interface AppointmentObserver {
    void update(Appointment appointment);
}
