package com.example.demo.notification;

import com.example.demo.model.Appointment;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSubject {
    private List<AppointmentObserver> observers = new ArrayList<>();

    public void attach(AppointmentObserver observer) {
        observers.add(observer);
    }

    public void detach(AppointmentObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Appointment appointment) {
        for(AppointmentObserver observer : observers) {
            observer.update(appointment);
        }
    }
}