package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/payment/{appointmentId}")
    public String paymentPage(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("amount", appointment.getAmount());
        return "payment";
    }
}
