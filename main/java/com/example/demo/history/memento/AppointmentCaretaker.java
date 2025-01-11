package com.example.demo.history.memento;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
public class AppointmentCaretaker {
    @Autowired
    private AppointmentHistory history;

    public void saveState(AppointmentMemento memento) {
        history.saveMemento(memento);
    }

    public List<AppointmentMemento> getStates(Long appointmentId) {
        return history.getHistory(appointmentId);
    }
}