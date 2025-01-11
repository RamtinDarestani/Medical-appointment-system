package com.example.demo.history.memento;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class AppointmentHistory {
    private final Map<Long, List<AppointmentMemento>> history = new HashMap<>();

    public void saveMemento(AppointmentMemento memento) {
        history.computeIfAbsent(memento.getAppointmentId(), k -> new ArrayList<>())
                .add(memento);
    }

    public List<AppointmentMemento> getHistory(Long appointmentId) {
        return history.getOrDefault(appointmentId, new ArrayList<>());
    }
}
