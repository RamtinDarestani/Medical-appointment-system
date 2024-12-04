package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.model.Availability;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Doctor registerDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Transactional
    public void saveAvailability(Long doctorId, Map<String, List<String>> schedule) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("پزشک یافت نشد"));

        availabilityRepository.deleteByDoctor(doctor);

        schedule.forEach((date, times) -> {
            times.forEach(time -> {
                LocalDateTime startTime = LocalDateTime.parse(date + "T" + time);
                LocalDateTime endTime = startTime.plusHours(1);
                Availability availability = new Availability(doctor, startTime, endTime, false);
                availabilityRepository.save(availability);
            });
        });
    }

    public List<Availability> getDoctorAvailability(Long doctorId, LocalDateTime from) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("پزشک یافت نشد"));
        return availabilityRepository.findByDoctorAndStartTimeAfterAndIsBookedFalse(doctor, from);
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public Doctor updateDoctorProfile(Long doctorId, Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("پزشک یافت نشد"));
        doctor.setName(updatedDoctor.getName());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        doctor.setAddress(updatedDoctor.getAddress());
        return doctorRepository.save(doctor);
    }

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Availability> getAvailableTimes(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("پزشک یافت نشد"));
        return availabilityRepository.findByDoctorAndIsBookedFalse(doctor);
    }
}
