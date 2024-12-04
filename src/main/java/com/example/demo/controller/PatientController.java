package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody Patient patient) {
        try {
            Patient registeredPatient = patientService.registerPatient(patient);
            return ResponseEntity.ok(registeredPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا در ثبت نام: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            Patient patient = patientService.loginPatient(email, password);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا در ورود: " + e.getMessage());
        }
    }
}
