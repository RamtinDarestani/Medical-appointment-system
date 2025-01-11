package com.example.demo.repository;

import com.example.demo.model.Availability;
import com.example.demo.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    void deleteByDoctor(Doctor doctor);
    List<Availability> findByDoctorAndStartTimeAfterAndIsBookedFalse(Doctor doctor, LocalDateTime startTime);
    List<Availability> findByDoctorAndIsBookedFalse(Doctor doctor);
}