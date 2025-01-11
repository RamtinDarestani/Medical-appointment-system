package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.history.memento.AppointmentMemento;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(@RequestParam Long doctorId,
                                                       @RequestParam Long patientId,
                                                       @RequestParam LocalDateTime appointmentTime) {
        return ResponseEntity.ok(appointmentService.bookAppointment(doctorId, patientId, appointmentTime));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Long doctorId,
                                                                   @RequestParam LocalDateTime start,
                                                                   @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(doctorId, start, end));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getPatientAppointments(patientId));
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long appointmentId,
                                                               @RequestParam String status) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(appointmentId, status));
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, Long> bookingData) {
        Long patientId = bookingData.get("patientId");
        Long availabilityId = bookingData.get("availabilityId");
        try {
            Appointment appointment = appointmentService.bookAppointment(patientId, availabilityId);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا در رزرو نوبت: " + e.getMessage());
        }
    }

    @GetMapping("/{appointmentId}/history")
    public ResponseEntity<List<AppointmentMemento>> getAppointmentHistory(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentHistory(appointmentId));
    }
}