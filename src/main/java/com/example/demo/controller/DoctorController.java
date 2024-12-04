package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.Availability;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.registerDoctor(doctor));
    }

    @PostMapping("/{doctorId}/availability")
    public ResponseEntity<?> addAvailability(@PathVariable Long doctorId, @RequestBody Map<String, List<String>> schedule) {
        try {
            doctorService.saveAvailability(doctorId, schedule);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<List<Availability>> getDoctorAvailability(@PathVariable Long doctorId, @RequestParam LocalDateTime from) {
        return ResponseEntity.ok(doctorService.getDoctorAvailability(doctorId, from));
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> updateDoctorProfile(@PathVariable Long doctorId, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.updateDoctorProfile(doctorId, doctor));
    }
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{doctorId}/available-times")
    public ResponseEntity<List<Availability>> getAvailableTimes(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAvailableTimes(doctorId));
    }
}
