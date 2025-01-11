package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class AuthController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        System.out.println("تلاش برای ورود - ایمیل: " + email + "، رمز عبور: " + password);

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor != null) {
            System.out.println("پزشک یافت شد - رمز عبور ذخیره شده: " + doctor.getPassword());
            if (doctor.getPassword().equals(password)) {
                return ResponseEntity.ok(Map.of("success", true, "doctorId", doctor.getId()));
            }
        }
        return ResponseEntity.ok(Map.of("success", false));
    }
}