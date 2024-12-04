package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient(Patient patient) {
        // بررسی تکراری نبودن ایمیل
        if (patientRepository.findByEmail(patient.getEmail()) != null) {
            throw new RuntimeException("این ایمیل قبلاً ثبت شده است");
        }
        // رمزنگاری پسورد (در یک پروژه واقعی باید از یک الگوریتم رمزنگاری مناسب استفاده شود)
        patient.setPassword(encryptPassword(patient.getPassword()));
        return patientRepository.save(patient);
    }

    public Patient loginPatient(String email, String password) {
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null || !checkPassword(password, patient.getPassword())) {
            throw new RuntimeException("ایمیل یا رمز عبور اشتباه است");
        }
        return patient;
    }

    private String encryptPassword(String password) {
        return password;
    }

    private boolean checkPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}