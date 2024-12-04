package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/register-doctor")
    public String registerDoctorPage() {
        return "register-doctor";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/patient-register")
    public String registerPatientPage() {
        return "patient-register";
    }

    @GetMapping("/patient-login")
    public String loginPatient() {
        return "patient-login";
    }

    @GetMapping("/schedule")
    public String schedulePage() {
        return "schedule";
    }

    @GetMapping("/patient-dashboard")
    public String patientDashboard() {
        return "patient-dashboard";
    }

    @GetMapping("/doctor-availability")
    public String doctorAvailability() {
        return "doctor-availability";
    }
}