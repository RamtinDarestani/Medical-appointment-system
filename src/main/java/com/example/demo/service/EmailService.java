package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentConfirmationEmail(String toEmail, String patientName,
                                                 String doctorName,
                                                 LocalDateTime appointmentTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ایمیل شما");
        message.setTo(toEmail);
        message.setSubject("نوبت جدید پزشکی");
        message.setText(String.format(
                "پزشک محترم %s\n\n" +
                        "یک نوبت جدید توسط بیمار %s رزرو شده است.\n" +
                        "جزئیات نوبت:\n" +
                        "تاریخ و زمان: %s\n" +
                        "با احترام، سیستم مدیریت نوبت",
                doctorName,
                patientName,
                appointmentTime.toString()
        ));

        mailSender.send(message);
    }
}
