package com.example.demo.service;

import com.example.demo.history.memento.AppointmentCaretaker;
import com.example.demo.history.memento.AppointmentMemento;
import com.example.demo.model.Appointment;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Availability;
import com.example.demo.notification.AppointmentSubject;
import com.example.demo.notification.EmailService;
import com.example.demo.payment.factory.PaymentFactory;
import com.example.demo.payment.strategy.OnlinePayment;
import com.example.demo.payment.strategy.PaymentStatus;
import com.example.demo.payment.strategy.PaymentStrategy;
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

    @Autowired
    private AppointmentCaretaker appointmentCaretaker;

    private final AppointmentSubject appointmentSubject;
    private final EmailService emailService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository,
                              AvailabilityRepository availabilityRepository,
                              EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.availabilityRepository = availabilityRepository;
        this.emailService = emailService;
        this.appointmentSubject = new AppointmentSubject();
        this.appointmentSubject.attach(emailService);
    }

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

    public Appointment processPayment(Long appointmentId, String paymentMethod, Object... paymentArgs) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("نوبت یافت نشد"));

        PaymentStrategy paymentStrategy = PaymentFactory.createPaymentStrategy(paymentMethod, paymentArgs);
        double amount = paymentStrategy.calculatePaymentAmount(appointment.getAmount());

        appointment.setPaymentStatus(PaymentStatus.PROCESSING);
        appointmentCaretaker.saveState(
                appointment.createMemento("PAYMENT_PROCESSING", "شروع پردازش پرداخت")
        );
        appointmentRepository.save(appointment);

        if (paymentStrategy.processPayment(amount)) {
            appointment.setPaymentMethod(paymentStrategy.getPaymentMethod());
            appointment.setPaymentStatus(PaymentStatus.PAID);
            if (paymentStrategy instanceof OnlinePayment) {
                appointment.setTransactionId(((OnlinePayment) paymentStrategy).getTransactionId());
            }
            appointmentCaretaker.saveState(
                    appointment.createMemento("PAYMENT_SUCCESS", "پرداخت موفق - مبلغ: " + amount)
            );
            return appointmentRepository.save(appointment);
        }

        appointment.setPaymentStatus(PaymentStatus.FAILED);
        appointmentCaretaker.saveState(
                appointment.createMemento("PAYMENT_FAILED", "پرداخت ناموفق")
        );
        appointmentRepository.save(appointment);
        throw new RuntimeException("پرداخت ناموفق بود");
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

        String oldStatus = appointment.getStatus();
        appointment.setStatus(status);

        appointmentCaretaker.saveState(
                appointment.createMemento("STATUS_CHANGE",
                        "تغییر وضعیت نوبت از " + oldStatus + " به " + status)
        );

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

        Appointment savedAppointment = appointmentRepository.save(appointment);
        appointmentSubject.notifyObservers(savedAppointment);

        return savedAppointment;
    }

    public List<AppointmentMemento> getAppointmentHistory(Long appointmentId) {
        return appointmentCaretaker.getStates(appointmentId);
    }

    public Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("نوبت یافت نشد"));
    }

}