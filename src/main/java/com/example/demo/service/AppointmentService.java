package com.example.demo.service;
import com.example.demo.model.Appointment;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Availability;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.AvailabilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Availability availability = availabilityRepository.findByDoctorAndStartTimeAfterAndIsBookedFalse(doctor, appointmentTime)
                .stream()
                .filter(a -> a.getStartTime().equals(appointmentTime))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available slot"));

        availability.setBooked(true);
        availabilityRepository.save(availability);

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus("SCHEDULED");

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getDoctorAppointments(Long doctorId, LocalDateTime start, LocalDateTime end) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor, start, end);
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return appointmentRepository.findByPatient(patient);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    @Transactional
    public Appointment bookAppointment(Long patientId, Long availabilityId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("بیمار یافت نشد"));
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("زمان مورد نظر یافت نشد"));

        if (availability.isBooked()) {
            throw new RuntimeException("این زمان قبلاً رزرو شده است");
        }

        availability.setBooked(true);
        availabilityRepository.save(availability);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(availability.getDoctor());
        appointment.setAppointmentTime(availability.getStartTime());
        appointment.setStatus("SCHEDULED");

        return appointmentRepository.save(appointment);
    }
}
